package com.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.CurrentUserSession;
import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.LoginException;
import com.store.module.Category;
import com.store.repository.CategoryRepo;
import com.store.repository.CurrentUserSessionRepo;

@Service
public class CategoryServicesImpl implements CategoryServices{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Override
	public Category addCategory(Category category, String key)throws CategoryException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can add category ");
		}
		
		Category cat = categoryRepo.findByCategoryName(category.getCategoryName());
		
		if(cat!=null) {
			throw new CategoryException("Category with this category name already exist => "+category.getCategoryName());
		}else {
			return categoryRepo.save(category);
		}

	}

	@Override
	public Category updateCategory(Category category, String key)throws CategoryException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update Category details please login first ");
		}
		
		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can update category ");
		}
		
        Optional<Category> cat = categoryRepo.findById(category.getCategoryId());
		
		if(cat.isEmpty()) {
			throw new CategoryException("No category found with this category id  => "+category.getCategoryId());
		}else {
			Category opt = cat.get();
			Category fn = categoryRepo.findByCategoryName(category.getCategoryName());
			if(fn!=null) {
				throw new CategoryException("Category with this category name already exist => "+category.getCategoryName());
			}else {
				opt.setCategoryName(category.getCategoryName());
				return categoryRepo.save(opt);
			}
			
		}
	}

	@Override
	public Category deleteCategory(String categoryName, String key)throws CategoryException, AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}
		
		if (loggedInUser.getAdmin() == false) {
			throw new AdminException("Only admin can delete category ");
		}
		
		Category cat = categoryRepo.findByCategoryName(categoryName);
			if(cat==null) {
				throw new CategoryException("No category found with this category name => "+categoryName);
			}else {
				categoryRepo.delete(cat);
				return cat;
			}
	}

	@Override
	public Category getCategoryByCategoryName(String categoryName) throws CategoryException {
		
        Category cat = categoryRepo.findByCategoryName(categoryName);
		if(cat==null) {
			throw new CategoryException("No category found with this category name => "+categoryName);
		}else {
			return cat;
		}
	}

	@Override
	public List<Category> getAllCategorys() throws CategoryException {
		List<Category> Categorys = categoryRepo.findAll();
		if(Categorys.isEmpty()) {
			throw new CategoryException("No category found ");
		}else {
			return Categorys;
		}
	}

}
