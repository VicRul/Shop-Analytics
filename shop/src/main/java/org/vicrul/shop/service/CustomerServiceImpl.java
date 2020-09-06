package org.vicrul.shop.service;

import java.util.List;
import org.vicrul.shop.dao.CustomerDAO;
import org.vicrul.shop.dao.CustomerDAOImpl;
import org.vicrul.shop.model.Customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService{

	private final CustomerDAO customerDao = new CustomerDAOImpl();
	
	@Override
	public List<Customer> findBySurname(String surname) {
		return customerDao.findBySurname(surname);
	}

	@Override
	public List<Customer> findWhoBoughtThisProduct(String title, long minTimes) {
		return customerDao.findWhoBoughtThisProduct(title, minTimes);
	}

	@Override
	public List<Customer> intervalCost(long minPrice, long maxPrice) {
		return customerDao.intervalCost(minPrice, maxPrice);
	}

	@Override
	public List<Customer> findPassiveCustomers(int count) {
		return customerDao.findPassiveCustomers(count);
	}

	@Override
	public void createView(String startDate, String endDate) {
		customerDao.createView(startDate, endDate);
	}

	@Override
	public void dropView() {
		customerDao.dropView();
	}
}
