package com.store.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.CurrentUserSession;
import com.store.dto.CustomerDto;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.module.Address;
import com.store.module.Customer;
import com.store.repository.CurrentUserSessionRepo;
import com.store.repository.CustomerRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepo customerrepo;

	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Override
	public Customer signUpCustomer(Customer customer) throws CustomerException {
		Customer existingCustomer = customerrepo.findByCustomerUsername(customer.getCustomerUsername());

		if (existingCustomer != null) {
			throw new CustomerException("Customer already exist with this username => " + customer.getCustomerUsername());
		}else {
			return customerrepo.save(customer);
		}
	}

	@Override
	public Customer updateCustomerDetails(Customer customer, String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update customer details please login first ");
		}

		if (customer.getCustomerId() == loggedInUser.getUserId()&&!loggedInUser.getAdmin()) {
			return customerrepo.save(customer);
		} else {
			throw new CustomerException("Provided customer details are incorrect please check CustomerException ID and other details and try again ");
		}
	}

	@Override
	public Customer addAddress(Address address, String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update customer details please login first ");
		}else if(!loggedInUser.getAdmin()) {
			Optional<Customer> opt = customerrepo.findById(loggedInUser.getUserId());
			Customer ccustomer = opt.get();
			ccustomer.getAddresses().add(address);
			customerrepo.save(ccustomer);
			return ccustomer;
		}else {
			throw new CustomerException("Login with customer account to add address ");
		}
	}

	@Override
	public Customer deleteCustomerAccount(String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To delete customer account login first ");
		}else if(!loggedInUser.getAdmin()) {
			Optional<Customer> opt = customerrepo.findById(loggedInUser.getUserId());
			Customer ccustomer = opt.get();
			customerrepo.delete(ccustomer);
			return ccustomer;
		}else {
			throw new CustomerException("Login with customer account to add address ");
		}

	}

	@Override
	public Customer findCustomerByCustomerUserName(String customerUserName) throws CustomerException {
		Customer existingCustomer = customerrepo.findByCustomerUsername(customerUserName);

		if (existingCustomer == null) {
			throw new CustomerException("No customer found with this customer user name => " + customerUserName);	
		}
		else {
			return existingCustomer;
		}
	}

	@Override
	public Customer findCustomerByCustomerId(Integer customerId) throws CustomerException {
		Optional<Customer> opt = customerrepo.findById(customerId);

		if (opt.isEmpty()) {
			throw new CustomerException("No customer found with this customer ID => " + customerId);
		}else {
			return opt.get();
		}
	}

	@Override
	public List<Customer> findAllCustomers() throws CustomerException {
		List<Customer> customerlist = customerrepo.findAll();
		if(customerlist.isEmpty()) {
			throw new CustomerException("No customer found");
		}else {
			return customerlist;
		}
	}

	@Override
	public CurrentUserSession loginCustomer(CustomerDto customer) throws LoginException {
		Customer existingUser = customerrepo.findByCustomerUsername(customer.getCustomerUsername());

		if (existingUser == null) {
			throw new LoginException("No customer found with this username => " + customer.getCustomerUsername());	
		}
		
		Optional<CurrentUserSession> validCustomerSessionOpt = currentUserSessionRepo.findById(existingUser.getCustomerId());

		if (validCustomerSessionOpt.isPresent() && existingUser.getCustomerPassword().equals(customer.getCustomerPassword())) {
			currentUserSessionRepo.delete(validCustomerSessionOpt.get());
		}

		if (existingUser.getCustomerPassword().equals(customer.getCustomerPassword())) {

			String key = RandomString.make(6);

			Boolean isAdmin = false;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getCustomerId(), key, isAdmin,
					LocalDateTime.now());

			currentUserSessionRepo.save(currentUserSession);

			return currentUserSession;
		} else {
			throw new LoginException("Password entered is incorrect ");
		}
	}

	@Override
	public String logoutCustomer(String key) throws LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid current user session key ");
		}
		currentUserSessionRepo.delete(loggedInUser);
		return "Logged Out !";
	}

}
