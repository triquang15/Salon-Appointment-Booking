package com.triquang.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triquang.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Set<Category> findBySalonId(Long salonId);
}
