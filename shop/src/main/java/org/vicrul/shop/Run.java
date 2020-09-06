package org.vicrul.shop;

import java.util.List;

import org.vicrul.shop.model.Customer;
import org.vicrul.shop.service.CustomerService;
import org.vicrul.shop.service.CustomerServiceImpl;

public class Run {

	public static void main(String[] args) {
		
		CustomerService customerService = new CustomerServiceImpl();
		List<Customer> customers = customerService.findBySurname("Иванов");
		
		for (Customer customer : customers) {
			System.out.println(customer.toString());
		}
	}

}
