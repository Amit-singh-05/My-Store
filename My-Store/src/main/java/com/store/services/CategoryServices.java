package com.store.services;

import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.LoginException;
import com.store.module.Category;

public interface CategoryServices {
	public Category addCategory(Category category, String key) throws CategoryException, AdminException, LoginException;

	public Category updateCategory(Category category, String key) throws CategoryException, AdminException, LoginException;

	public Category deleteCategory(String categoryName, String key) throws CategoryException, AdminException, LoginException;
	
	public Category getCategoryByCategoryName(String categoryName) throws CategoryException;
}
