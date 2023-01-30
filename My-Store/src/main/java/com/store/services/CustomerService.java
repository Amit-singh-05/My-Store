package com.store.services;

import java.util.List;

import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.module.Customer;

public interface CustomerService {
	public Customer signUpCustomer(Customer customer) throws CustomerException;

	public Customer updateCustomerDetails(Customer customer, String key) throws CustomerException,LoginException;

	public Customer deleteCustomerAccount(String key) throws CustomerException,LoginException;

	public Customer findCustomerByCustomerUserName(String customerUserName) throws CustomerException;
	
	public List<Customer> findAllCustomer() throws CustomerException;
	
	
}
