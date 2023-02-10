package com.store.services;

import java.util.List;

import com.store.dto.CurrentUserSession;
import com.store.dto.CustomerDto;
import com.store.exception.AddressException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.module.Address;
import com.store.module.Customer;

public interface CustomerService {
	
	public Customer signUpCustomer(Customer customer) throws CustomerException;

	public Customer updateCustomerDetails(Customer customer, String key) throws CustomerException,LoginException;

	public Customer addAddress(Address address, String key) throws CustomerException,LoginException;
	
	public Customer deleteAddress(Integer addressId, String key) throws CustomerException,LoginException,AddressException;
	
	public Customer deleteCustomerAccount(String key) throws CustomerException,LoginException;

	public Customer findCustomerByCustomerUserName(String customerUserName) throws CustomerException;
	
	public Customer findCustomerByCustomerId(Integer customerId) throws CustomerException;
	
	public List<Customer> findAllCustomers() throws CustomerException;
	
	public CurrentUserSession loginCustomer(CustomerDto customer) throws LoginException;
		
	public String logoutCustomer(String key) throws LoginException;
	
}
