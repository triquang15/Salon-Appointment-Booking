package com.triquang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Category;
import com.triquang.modal.payload.SalonDto;
import com.triquang.service.CategoryService;
import com.triquang.service.client.SalonFeignClient;

@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SalonFeignClient salonFeignClient;

	@PostMapping
	public ResponseEntity<Category> createCategory(
	        @RequestBody Category category,
	        @RequestHeader("Authorization") String token) throws Exception {

	   var salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();

	    if (salonList == null || salonList.isEmpty()) {
	        throw new Exception("No salon found for this owner!");
	    }

	   // Validate salon belongs to this owner
	    var salonDto = salonList.stream()
	            .filter(s -> s.getId().equals(category.getSalonId()))
	            .findFirst()
	            .orElseThrow(() -> new Exception("Salon ID does not belong to this owner!"));

	    var savedCategory = categoryService.saveCategory(category, salonDto);
 
	    return ResponseEntity.ok(savedCategory);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategoryById(
	        @PathVariable Long categoryId,
	        @RequestHeader("Authorization") String token) throws Exception {

	    // 1. Get owner salons
	    List<SalonDto> salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();

	    if (salonList == null || salonList.isEmpty()) {
	        throw new Exception("No salon found for this owner!");
	    }

	    // 2. Get category to know salonId
	    Category category = categoryService.getCategoryById(categoryId);

	    if (category == null) {
	        throw new Exception("Category not found!");
	    }

	    Long salonIdFromCategory = category.getSalonId();

	    // 3. Validate the salonId belongs to the owner
	    boolean belongsToOwner = salonList.stream()
	            .anyMatch(s -> s.getId().equals(salonIdFromCategory));

	    if (!belongsToOwner) {
	        throw new Exception("You do not have permission to delete this category!");
	    }

	    // 4. Delete
	    categoryService.deleteCategoryById(categoryId, salonIdFromCategory);

	    return ResponseEntity.ok("Category deleted successfully");
	}
	
	@GetMapping("/salon/{salonId}/category/{id}")
	public ResponseEntity<Category> getCategoriesByIdAndSalon(@PathVariable Long salonId, @PathVariable Long id, @RequestHeader("Authorization") String token) throws Exception {
		var category = categoryService.findByIdAndSalonId(id, salonId);
		return ResponseEntity.ok(category);
	}
}
