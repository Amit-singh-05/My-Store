package com.store.services;

import java.util.List;

import com.store.enums.OrderStatus;
import com.store.exception.AddressException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.OrdersException;
import com.store.exception.ProductException;
import com.store.module.Orders;

public interface OrdersServices {
	public Orders placeOrder(Orders orders,Integer addressId,String key) throws ProductException,AddressException,OrdersException,CustomerException, LoginException;
	public Orders cancelOrder(Integer orderId,String key) throws ProductException,OrdersException,CustomerException, LoginException;
	public List<Orders> getAllOrders(String key) throws OrdersException,CustomerException, LoginException;
	public List<Orders> getAllOrdersByCustomer(String key) throws OrdersException,CustomerException, LoginException;
	public Orders UpdateDeliveryAddress(Integer orderId,Integer addressId,String key) throws OrdersException,CustomerException, LoginException,AddressException;
	public Orders UpdateDeliveryStatus(Integer orderId,String status,String key) throws OrdersException,CustomerException, LoginException,AddressException;
}
