package com.triquang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Category;
import com.triquang.modal.payload.SalonDto;
import com.triquang.service.CategoryService;

@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		SalonDto salonDto = new SalonDto();
		salonDto.setId(1L); // Hardcoded salon ID for demonstration purposes
		Category savedCategory = categoryService.saveCategory(category, salonDto);
		return ResponseEntity.ok(savedCategory);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) throws Exception {
		SalonDto salonDto = new SalonDto();
		salonDto.setId(1L); // Hardcoded salon ID for demonstration purposes

		categoryService.deleteCategoryById(categoryId, salonDto.getId());
		return ResponseEntity.ok("Category deleted successfully");
	}
}
