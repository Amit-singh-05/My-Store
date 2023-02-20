package com.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.exception.AddressException;
import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.OrdersException;
import com.store.exception.ProductException;
import com.store.module.Orders;
import com.store.module.Product;
import com.store.services.OrdersServices;

@RestController
@RequestMapping("/ordersController")
public class OrdersController {
	@Autowired
	private OrdersServices ordersServices;
	
	@PutMapping("/placeOrder")
	public ResponseEntity<Orders> placeOrderHandler(@RequestBody Orders orders,@RequestParam Integer addressId,@RequestParam String key) throws ProductException, OrdersException, CustomerException, LoginException, AddressException{

		Orders neworder = ordersServices.placeOrder(orders, addressId, key);

		return new ResponseEntity<Orders>(neworder, HttpStatus.OK);

	}
	
	@DeleteMapping("/cancelOrder")
	public ResponseEntity<Orders> cancelOrderHandler(@RequestParam Integer orderId,@RequestParam String key) throws ProductException, OrdersException, CustomerException, LoginException{

		Orders cancelledOrder  = ordersServices.cancelOrder(orderId, key);

		return new ResponseEntity<Orders>(cancelledOrder, HttpStatus.OK);
	}
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<List<Orders>> findAllOrdersHandler(@RequestParam String key) throws OrdersException, CustomerException, LoginException{

		List<Orders> orders = ordersServices.getAllOrders(key);

		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);

	}
	
	@GetMapping("/getAllOrdersByCustomerID")
	public ResponseEntity<List<Orders>> findAllOrdersByCustomerIDHandler(@RequestParam String key) throws OrdersException, CustomerException, LoginException{

		List<Orders> orders = ordersServices.getAllOrdersByCustomer(key);

		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);

	}
}
