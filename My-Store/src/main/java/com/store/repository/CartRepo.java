package com.store.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.store.module.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

}
