package org.vicrul.shop;

import java.util.List;

import org.vicrul.shop.model.Customer;
import org.vicrul.shop.service.CustomerService;
import org.vicrul.shop.service.CustomerServiceImpl;

public class Run {

	public static void main(String[] args) {
		
		CustomerService customerService = new CustomerServiceImpl();
		List<Customer> customers1 = customerService.findBySurname("Иванов");
		List<Customer> customers2 = customerService.findWhoBoughtThisProduct("Минералка", 0);
		List<Customer> customers3 = customerService.intervalCost(100, 500);
		List<Customer> customers4 = customerService.findPassiveCustomers(4);
		//customerService.createView("2020-05-20", "2020-05-24");
		//customerService.dropView();
		
		getList(customers1);
		System.out.println("==============================================================");
		getList(customers2);
		System.out.println("==============================================================");
		getList(customers3);
		System.out.println("==============================================================");
		getList(customers4);
		
	}
	
	public static List<Customer> getList(List<Customer> customers) {
		for (Customer customer : customers) {
			System.out.println(customer.toString());
		}
		return customers;		
	}
}