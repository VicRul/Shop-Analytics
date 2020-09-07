package org.vicrul.shop.service;

import java.util.List;
import org.vicrul.shop.dao.CustomerDAO;
import org.vicrul.shop.dao.CustomerDAOImpl;
import org.vicrul.shop.model.Customer;
import org.vicrul.shop.model.PeriodData;
import org.vicrul.shop.model.Product;

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
	public List<Customer> findPassiveCustomers(long count) {
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

	@Override
	public List<Customer> getCustomersInPeriod() {
		return customerDao.getCustomersInPeriod();
	}

	@Override
	public List<Product> getCustomerPurchses(String surname, String name) {
		return customerDao.getCustomerPurchses(surname, name);
	}

	@Override
	public List<PeriodData> getAllData() {
		return customerDao.getAllData();
	}
}
