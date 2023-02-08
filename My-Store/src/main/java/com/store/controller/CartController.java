package com.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Cart;
import com.store.module.Category;
import com.store.services.CartServices;
import com.store.services.CategoryServices;

@RestController
@RequestMapping("/cartController")
public class CartController {
	@Autowired
	private CartServices cartServices;
	
	@PutMapping("/addProductToCart")
	public ResponseEntity<Cart> addProductToCartHandler(@RequestParam String productName, @RequestParam Integer quantity ,@RequestParam String key) throws ProductException, CustomerException, LoginException{

		Cart cart = cartServices.addProductToCart(productName, quantity, key);

		return new ResponseEntity<Cart>(cart, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteProductFromCart")
	public ResponseEntity<Cart> deleteProductFromCartHandler(@RequestParam("categoryName") String categoryName, @RequestParam String key) throws ProductException, CustomerException, LoginException{

		Cart deletedProduct = cartServices.deleteProductFromCart(categoryName, key);

		return new ResponseEntity<Cart>(deletedProduct, HttpStatus.OK);
	}
}
