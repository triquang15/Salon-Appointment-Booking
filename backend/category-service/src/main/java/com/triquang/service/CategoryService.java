package com.triquang.service;

import java.util.Set;

import com.triquang.modal.Category;
import com.triquang.modal.payload.SalonDto;

public interface CategoryService {
	Category saveCategory(Category category, SalonDto salonDto);
	Set<Category> getAllCategoriesBySalonId(Long salonId);
	Category getCategoryById(Long categoryId) throws Exception;
	void deleteCategoryById(Long categoryId, Long salonId) throws Exception;
	Category findByIdAndSalonId(Long id, Long salonId) throws Exception;
}
