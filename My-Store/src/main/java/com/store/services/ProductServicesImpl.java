package com.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.store.dto.CurrentUserSession;
import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.LoginException;
import com.store.exception.ProductException;
import com.store.module.Category;
import com.store.module.Product;
import com.store.repository.CategoryRepo;
import com.store.repository.CurrentUserSessionRepo;
import com.store.repository.ProductRepo;

public class ProductServicesImpl implements ProductServices{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Override
	public Product addProduct(Product product, String categoryName, String key)throws ProductException, CategoryException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can add product ");
		}
		
		Category cat = categoryRepo.findByCategoryName(categoryName);
		
		if(cat!=null) {
			throw new CategoryException("Category with this category name already exist => "+categoryName);
		}else {
			cat.getProductList().add(product);
			categoryRepo.save(cat);
			return product;
		}
	}

	@Override
	public Product updateProduct(Product product, String key) throws ProductException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}
		
		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can update product ");
		}
		
        Optional<Product> pro = productRepo.findById(product.getProductId());
		
		if(pro.isEmpty()) {
			throw new ProductException("No product found with this product id  => "+product.getProductId());
		}else {
				return productRepo.save(product);
		}
	}

	@Override
	public Product deleteProduct(Integer productId, String key)throws ProductException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}
		
		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can delete product ");
		}
		
		Optional<Product> pro = productRepo.findById(productId);
		if(pro.isEmpty()) {
			throw new ProductException("No product found with this product id => "+productId);
		}else {
			productRepo.delete(pro.get());
			return pro.get();
		}
	}

	@Override
	public Product getProductByProductName(String productName) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByCategory(String categoryName) throws ProductException, CategoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

}
