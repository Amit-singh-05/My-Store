package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.module.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {

}
