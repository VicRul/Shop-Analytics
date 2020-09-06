package org.vicrul.shop.dao;

import java.util.List;

import org.vicrul.shop.model.Customer;

public interface CustomerDAO {

	public List<Customer> findBySurname(String surname);
	public List<Customer> findWhoBoughtThisProduct(String title, long minTimes);
	public List<Customer> intervalCost(long minPrice, long maxPrice);
	public List<Customer> findPassiveCustomers(int count);
}
