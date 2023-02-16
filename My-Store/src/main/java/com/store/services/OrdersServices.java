package com.store.services;

import com.store.exception.AddressException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.OrdersException;
import com.store.exception.ProductException;
import com.store.module.Orders;

public interface OrdersServices {
	public Orders placeOrder(Orders orders,Integer addressId,String key) throws ProductException,AddressException,OrdersException,CustomerException, LoginException;
	public Orders cancelOrder(Integer orderId,String key) throws ProductException,OrdersException,CustomerException, LoginException;
}
