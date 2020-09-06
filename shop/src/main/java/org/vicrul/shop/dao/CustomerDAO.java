package org.vicrul.shop.dao;

import java.util.List;

import org.vicrul.shop.model.Customer;

public interface CustomerDAO {

	public List<Customer> findBySurname(String surname); 
}
