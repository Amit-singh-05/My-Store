package com.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.exception.AdminException;
import com.store.exception.CategoryException;
import com.store.exception.LoginException;
import com.store.module.Admin;
import com.store.module.Category;
import com.store.services.CategoryServices;

@RestController
@RequestMapping("/categoryController")
public class CategoryController {
	@Autowired
	private CategoryServices categoryServices;
	
	@PostMapping("/addCategory")
	public ResponseEntity<Category> addCategoryHandler(@Valid @RequestBody Category category, @RequestParam String key) throws CategoryException, AdminException, LoginException{

		Category newCategory = categoryServices.addCategory(category, key);

		return new ResponseEntity<Category>(newCategory, HttpStatus.OK);

	}
	
	@PutMapping("/updateCategory")
	public ResponseEntity<Category> updateAdminDetailsHandler(@Valid @RequestBody Category category, @RequestParam("key") String key) throws CategoryException, AdminException, LoginException{

		Category updatedCategory = categoryServices.updateCategory(category, key);
		
		return new ResponseEntity<Category>(updatedCategory, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteCategory")
	public ResponseEntity<Category> deleteCategoryHandler(@RequestParam("categoryName") String categoryName, @RequestParam String key) throws CategoryException, AdminException, LoginException{

		Category deletedCategory = categoryServices.deleteCategory(categoryName, key);

		return new ResponseEntity<Category>(deletedCategory, HttpStatus.OK);
	}
	
	@GetMapping("/categoryByCategoryName")
	public ResponseEntity<Category> findCategoryByCategoryNameHandler(@RequestParam("categoryName") String categoryName) throws CategoryException{

		Category category = categoryServices.getCategoryByCategoryName(categoryName);

		return new ResponseEntity<Category>(category, HttpStatus.OK);

	}
	
	@GetMapping("/categorys")
	public ResponseEntity<List<Category>> findAllCategorysHandler() throws CategoryException{

		List<Category> categorys = categoryServices.getAllCategorys();

		return new ResponseEntity<List<Category>>(categorys, HttpStatus.OK);

	}
}
