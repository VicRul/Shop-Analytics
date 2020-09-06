package org.vicrul.shop.service;

import java.util.List;

import org.vicrul.shop.model.Customer;

public interface CustomerService {

	public List<Customer> findBySurname(String surname);
	public List<Customer> findWhoBoughtThisProduct(String title, long minTimes);
	public List<Customer> intervalCost(long minPrice, long maxPrice);
	public List<Customer> findPassiveCustomers(int count);
	public void createView(String startDate, String endDate);
	public void dropView();
}
