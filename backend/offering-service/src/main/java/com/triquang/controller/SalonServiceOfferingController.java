package com.triquang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Offering;
import com.triquang.payload.CategoryDto;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;
import com.triquang.service.OfferingService;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

	@Autowired
	private OfferingService offeringService;

	@PostMapping
	public ResponseEntity<Offering> createServiceOffering(@RequestBody OfferingDto offeringDto) {
		SalonDto salonDto = new SalonDto();
		salonDto.setId(1L);

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(offeringDto.getCategoryId());

		var offering = offeringService.createServiceOffering(salonDto, offeringDto, categoryDto);
		return ResponseEntity.ok(offering);

	}

	@PostMapping("/{id}")
	public ResponseEntity<Offering> updateServiceOffering(@PathVariable Long id, @RequestBody Offering offering) throws Exception {
		return ResponseEntity.ok(offeringService.updateServiceOffering(id, offering));
	}
}
