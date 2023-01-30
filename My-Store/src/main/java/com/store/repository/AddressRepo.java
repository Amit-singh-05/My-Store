package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.module.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{

}
