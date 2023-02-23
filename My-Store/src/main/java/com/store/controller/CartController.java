package com.store.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.exception.CategoryException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Cart;
import com.store.module.Category;
import com.store.module.Product;
import com.store.services.CartServices;

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
	
	@PutMapping("/updateProductQuantity")
	public ResponseEntity<Cart> updateProductQuantityHandler(@RequestParam String productName, @RequestParam Integer quantity ,@RequestParam String key) throws ProductException, CustomerException, LoginException {

		Cart updatedCart = cartServices.updateProductQuantity(productName, quantity, key);
		
		return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteProductFromCart")
	public ResponseEntity<Cart> deleteProductFromCartHandler(@RequestParam("productName") String productName, @RequestParam String key) throws ProductException, CustomerException, LoginException{

		Cart deletedProduct = cartServices.deleteProductFromCart(productName, key);

		return new ResponseEntity<Cart>(deletedProduct, HttpStatus.OK);
	}
	
	@GetMapping("/getAllProductsInCart")
	public ResponseEntity<Map<String, Integer>> getAllProductsInCartHandler(@RequestParam String key) throws ProductException, CustomerException, LoginException{

		Map<String, Integer> products = cartServices.getAllProductsInCart(key);

		return new ResponseEntity<Map<String, Integer>>(products, HttpStatus.OK);

	}
	
	@PutMapping("/removeAllProductsFromCart")
	public ResponseEntity<Cart> removeAllProductsFromCart(@RequestParam String key) throws ProductException, CustomerException, LoginException {

		Cart updatedCart = cartServices.removeAllProductsFromCart(key);
		
		return new ResponseEntity<Cart>(updatedCart, HttpStatus.OK);

	}
}
