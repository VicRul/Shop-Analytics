package org.vicrul.shop.service;

import java.util.List;
import org.vicrul.shop.model.Customer;
import org.vicrul.shop.model.PeriodData;
import org.vicrul.shop.model.Product;

public interface CustomerService {

	List<Customer> findBySurname(String surname);
	List<Customer> findWhoBoughtThisProduct(String title, long minTimes);
	List<Customer> intervalCost(long minPrice, long maxPrice);
	List<Customer> findPassiveCustomers(int count);
	void createView(String startDate, String endDate);
	void dropView();
	List<Customer> getCustomersInPeriod();
	List<Product> getCustomerPurchses(String surname, String name);
	List<PeriodData> getAllData();
}
