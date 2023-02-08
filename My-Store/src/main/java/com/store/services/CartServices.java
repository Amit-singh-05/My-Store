package com.store.services;

import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Cart;
import com.store.module.Product;

public interface CartServices {
	public Cart addProductToCart(String productName, Integer quantity ,String key) throws ProductException,CustomerException, LoginException;
	public Cart deleteProductFromCart(String productName,String key) throws ProductException,CustomerException, LoginException;
	
	
}
