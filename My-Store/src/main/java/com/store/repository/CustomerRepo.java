package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.store.module.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	public Customer findByCustomerUsername(String customerUsername);
}
