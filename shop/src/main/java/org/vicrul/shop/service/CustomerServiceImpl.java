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
}
