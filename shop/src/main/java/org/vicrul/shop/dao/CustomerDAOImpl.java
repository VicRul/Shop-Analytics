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
			TypedQuery<Customer> query = session.createQuery("select new Customer(id, surname, name) from Customer where surname =: surname", Customer.class)
					.setParameter("surname", surname);
			customers = query.getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		return customers;
	}
}
