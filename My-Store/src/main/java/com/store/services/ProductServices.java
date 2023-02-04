package com.store.services;

import java.util.List;

import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Product;

public interface ProductServices {
	public Product addProduct(Product product,String categoryName, String key) throws ProductException,CategoryException,AdminException, LoginException;

	public Product updateProduct(Product product, String key) throws ProductException,AdminException, LoginException;

	public Product deleteProduct(Integer productId,String key) throws ProductException,AdminException, LoginException;
	
	public Product getProductByProductName(String productName) throws ProductException;
	
	public List<Product> getProductsByCategory(String categoryName) throws ProductException,CategoryException;

	public List<Product> getAllProducts() throws ProductException;
}
