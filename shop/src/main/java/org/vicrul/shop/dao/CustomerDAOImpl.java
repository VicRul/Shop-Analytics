package org.vicrul.shop.dao;

import java.util.List;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.vicrul.shop.model.Customer;
import org.vicrul.shop.util.HibernateSessionFactoryUtil;

public class CustomerDAOImpl implements CustomerDAO {

	private Session getCurrentSession() {
		return HibernateSessionFactoryUtil.getSessionFactory().openSession();
	}

	@Override
	public List<Customer> findBySurname(String surname) {
		Session session = getCurrentSession();
		List<Customer> customers;
		try {
			session.beginTransaction();
			TypedQuery<Customer> query = session
					.createQuery("select new Customer(id, surname, name) from Customer where surname =: surname",
							Customer.class)
					.setParameter("surname", surname);
			customers = query.getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return customers;
	}

	@Override
	public List<Customer> findWhoBoughtThisProduct(String type, long minTimes) {
		Session session = getCurrentSession();
		List<Customer> customers;
		try {
			session.beginTransaction();
			TypedQuery<Customer> query = session
					.createQuery(
							"select new Customer(c.id, c.surname, c.name)"
									+ " from Purchase pur inner join pur.customer c inner join pur.product prod"
									+ " where prod.type = :type group by c having count(prod.id) >= :minTimes",
							Customer.class)
					.setParameter("type", type).setParameter("minTimes", minTimes);
			customers = query.getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return customers;
	}

	@Override
	public List<Customer> intervalCost(long minPrice, long maxPrice) {
		Session session = getCurrentSession();
		List<Customer> customers;
		try {
			session.beginTransaction();
			TypedQuery<Customer> query = session.createQuery(
					"select new Customer(c.id, c.surname, c.name)"
							+ " from Purchase pur inner join pur.customer c inner join pur.product prod"
							+ " group by c having sum(prod.price) >= :minPrice and sum(prod.price) <= :maxPrice",
					Customer.class).setParameter("minPrice", minPrice).setParameter("maxPrice", maxPrice);
			customers = query.getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return customers;
	}

	@Override
	public List<Customer> findPassiveCustomers(int count) {
		Session session = getCurrentSession();
		List<Customer> customers;
		try {
			session.beginTransaction();
			TypedQuery<Customer> query = session.createQuery("select new Customer(c.id, c.surname, c.name)"
					+ " from Purchase pur inner join pur.customer c inner join pur.product prod"
					+ " group by c order by count(prod.id)", Customer.class).setMaxResults(count);
			customers = query.getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return customers;
	}

	@Override
	public void createView(String startDate, String endDate) {
		Session session = getCurrentSession();
		try {
			session.beginTransaction();
			session.createSQLQuery(
					"create view all_data as select c.surname, c.name, pr.type, pr.price, pu.date_purchase " 
						+ "from products pr inner join purchases pu on pr.id = pu.product_id inner join customers c on c.id = pu.customer_id " 
						+ "where date_part('dow', pu.date_purchase) NOT IN (0,6) " 
						+ "and (pu.date_purchase >= '" + startDate + "' and pu.date_purchase <= '" + endDate + "')").executeUpdate();
			System.out.println("Создано");
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void dropView() {
		Session session = getCurrentSession();
		try {
			session.beginTransaction();
			session.createSQLQuery("drop view all_data").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}
}
