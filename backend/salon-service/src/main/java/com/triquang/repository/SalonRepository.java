package com.triquang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triquang.modal.Salon;

public interface SalonRepository extends JpaRepository<Salon, Long> {
	List<Salon> findByOwnerId(Long ownerId);
	
	@Query("SELECT s FROM Salon s WHERE" +
	       " LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
	       " LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
	       " LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Salon> searchSalonsByCity(@Param("keyword") String keyword);
}
