package com.store.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.CurrentUserSession;
import com.store.exception.AdminException;
import com.store.exception.CustomerException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Cart;
import com.store.module.Customer;
import com.store.module.Product;
import com.store.repository.CartRepo;
import com.store.repository.CurrentUserSessionRepo;
import com.store.repository.CustomerRepo;
import com.store.repository.ProductRepo;

@Service
public class CartServicesImpl implements CartServices{
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CustomerRepo customerrepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;
	
	@Override
	public Cart addProductToCart(String productName, Integer quantity, String key)
			throws ProductException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can add product to their cart please log in as customer ");
		}
		
		if (quantity==0) {
			throw new ProductException("Quantity of products to be added should be more than 0 ");
		}
		
		Product product = productRepo.findByProductName(productName);
		if(product==null) {
			throw new ProductException("No product found with this product name ");
		}else if(product!=null && product.getQuantity()<quantity) {
			throw new ProductException("Product available but the quantity is less ");
		}
		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if(copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
			Customer customer = copt.get();
			Cart cart = customer.getCart();
			Map<String,Integer> products = cart.getProducts();
			
			if(products.get(productName)==null) {
				products.put(productName, quantity);
			}else if(products.get(productName)+quantity<=product.getQuantity()) {
				products.put(productName, products.get(productName)+quantity);
			}else {
				throw new ProductException("Product available but the quantity is less ");
			}
			cartRepo.save(cart);
            return cart;
	}

	@Override
	public Cart deleteProductFromCart(String productName, String key)
			throws ProductException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can delete product from their cart please log in as customer ");
		}
		
		Product product = productRepo.findByProductName(productName);
		if(product==null) {
			throw new ProductException("No product found with this product name ");
		}
		
		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if(copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
			Customer customer = copt.get();
			Cart cart = customer.getCart();
			Map<String,Integer> products = cart.getProducts();
			
			if(products.get(productName)==null) {
				throw new ProductException("No product found with this product name in cart");
			}else {
				products.remove(productName);
			}
			cartRepo.save(cart);
            return cart;
	}
//
//	@Override
//	public Cart updateProductQuantity(String productName, Integer quantity, String key)
//			throws ProductException, CustomerException, LoginException {
//		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);
//
//		if (loggedInUser == null) {
//			throw new LoginException("Entered current user session key is invalid ");
//		}
//
//		if (loggedInUser.getAdmin()) {
//			throw new CustomerException("Only customer can update product quantity to their cart please log in as customer ");
//		}
//		
//		if (quantity==0) {
//			throw new ProductException("Quantity of products to be updated should be more than 0 ");
//		}
//		
//		Product product = productRepo.findByProductName(productName);
//		if(product==null) {
//			throw new ProductException("No product found with this product name ");
//		}else if(product!=null && product.getQuantity()<quantity) {
//			throw new ProductException("Product available but the quantity is less ");
//		}
//		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
//		if(copt.isEmpty()) {
//			throw new CustomerException("No customer data found with this ID ");
//		}
//			Customer customer = copt.get();
//			Cart cart = customer.getCart();
//			Map<Product,Integer> products = cart.getProducts();
//			
//			if(products.get(product)==null) {
//				throw new ProductException("No product found with this product name in cart ");
//			}else {
//				products.put(product, quantity);
//			}
//			cartRepo.save(cart);
//            return cart;
//	}
//
	@Override
	public Map<String, Integer> getAllProductsInCart(String key)
			throws ProductException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can access cart details please log in as customer ");
		}
		
		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if(copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
			Customer customer = copt.get();
			Cart cart = customer.getCart();
			Map<String,Integer> products = cart.getProducts();
			
			if(products.isEmpty()) {
				throw new ProductException("No product found ");
			}else {
				return products;	
			}
	}

}
