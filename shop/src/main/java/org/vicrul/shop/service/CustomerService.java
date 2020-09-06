package org.vicrul.shop.service;

import java.util.List;

import org.vicrul.shop.model.Customer;

public interface CustomerService {

	public List<Customer> findBySurname(String surname);
}
