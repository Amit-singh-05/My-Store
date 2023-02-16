package com.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
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
		
		if(cat==null) {
			throw new CategoryException("Category with this category name Does not exist => "+categoryName);
		}else {
			Product pro = productRepo.findByProductName(product.getProductName());
			if(pro != null) {
				throw new ProductException("Product already exist ");
			}else {
				cat.getProductList().add(product);
				product.setCategory(cat);
				return productRepo.save(product);
			}
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
	public Product deleteProduct(Integer productId, String categoryName, String key)throws ProductException, CategoryException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}
		
		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can delete product ");
		}
		
		Category category = categoryRepo.findByCategoryName(categoryName);
		if(category == null) {
			throw new CategoryException("No category found with this category name => "+categoryName);
		}else {
			Optional<Product> pp = productRepo.findById(productId);
			if(pp.isEmpty()) {
				throw new ProductException("No product found with this product id => "+productId);
			}else {
				List<Product> pro = category.getProductList();
				pro.remove(pp.get());
				productRepo.delete(pp.get());
			}
			return pp.get();
		}
	}

	@Override
	public Product getProductByProductName(String productName) throws ProductException {
		Product product = productRepo.findByProductName(productName);
		if(product == null) {
			throw new ProductException("No product found with this product name => "+productName);
		}else {
			return product;
		}
	}

	@Override
	public List<Product> getProductsByCategory(String categoryName) throws ProductException, CategoryException {
		Category category = categoryRepo.findByCategoryName(categoryName);
		if(category == null) {
			throw new CategoryException("No category found with this category name => "+categoryName);
		}else {
			List<Product> pro = category.getProductList();
			if(pro.isEmpty()) {
				throw new ProductException("No product found ");
			}else {
				return pro;
			}
		}
		
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {
		List<Product> pro = productRepo.findAll();
		if(pro.isEmpty()) {
			throw new ProductException("No product found ");
		}else {
			return pro;
		}
	}

}
