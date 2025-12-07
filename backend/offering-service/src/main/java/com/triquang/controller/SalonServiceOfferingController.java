package com.triquang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Offering;
import com.triquang.payload.CategoryDto;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;
import com.triquang.service.OfferingService;
import com.triquang.service.client.CategoryFeignClient;
import com.triquang.service.client.SalonFeignClient;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

	@Autowired
	private OfferingService offeringService;
	
	@Autowired
	private SalonFeignClient salonFeignClient;
	
	@Autowired
	private CategoryFeignClient categoryFeignClient;

	@PostMapping
	public ResponseEntity<Offering> createServiceOffering(
	        @RequestBody OfferingDto offeringDto,
	        @RequestHeader("Authorization") String token) throws Exception {

	    // 1. Get all salons belonging to owner (via token)
	    List<SalonDto> salonList = salonFeignClient.getSalonsByOwnerId(token).getBody();

	    if (salonList == null || salonList.isEmpty()) {
	        throw new Exception("No salon found for this owner!");
	    }

	    // 2. Validate the salonId inside OfferingDto actually belongs to that owner
	    SalonDto selectedSalon = salonList.stream()
	            .filter(s -> s.getId().equals(offeringDto.getSalonId()))
	            .findFirst()
	            .orElseThrow(() -> new Exception("Salon ID not found for this owner"));

	    // 3. Call Category Service to fetch the category (by salon + category ID)
	    ResponseEntity<CategoryDto> categoryResponse =
	            categoryFeignClient.getCategoriesByIdAndSalon(
	                    offeringDto.getSalonId(),                // salonId
	                    offeringDto.getCategoryId(),            // categoryId
	                    token                                   // Authorization header
	            );

	    CategoryDto categoryDto = categoryResponse.getBody();
	    if (categoryDto == null) {
	        throw new Exception("Category not found for this salon!");
	    }

	    // 4. Create offering
	    Offering offering = offeringService.createServiceOffering(
	            selectedSalon,
	            offeringDto,
	            categoryDto
	    );

	    return ResponseEntity.ok(offering);
	}


	@PostMapping("/{id}")
	public ResponseEntity<Offering> updateServiceOffering(@PathVariable Long id, @RequestBody Offering offering) throws Exception {
		return ResponseEntity.ok(offeringService.updateServiceOffering(id, offering));
	}
}
