package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.module.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

}
