package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.exception.AddressException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.OrdersException;
import com.store.exception.ProductException;
import com.store.module.Orders;
import com.store.services.OrdersServices;

@RestController
@RequestMapping("/ordersController")
public class OrdersController {
	@Autowired
	private OrdersServices ordersServices;
	
	@PutMapping("/placeOrder")
	public ResponseEntity<Orders> placeOrderHandler(@RequestBody Orders orders,@RequestParam Integer addressId,@RequestParam String key) throws ProductException, OrdersException, CustomerException, LoginException, AddressException{

		Orders neworder = ordersServices.PlaceOrder(orders, addressId, key);

		return new ResponseEntity<Orders>(neworder, HttpStatus.OK);

	}
}
