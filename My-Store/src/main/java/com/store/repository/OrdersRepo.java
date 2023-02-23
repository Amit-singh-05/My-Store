package com.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.module.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
	@Query("from Orders where customer.customerId=:idcustomer")
	public List<Orders> findBycustomerId(@Param("idcustomer") Integer idcardid);
	
	public List<Orders> findByOrderDate(LocalDate orderDate);
}
