package com.triquang.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Offering;
import com.triquang.service.OfferingService;

@RestController
@RequestMapping("/api/service-offering")
public class OfferingController {

	@Autowired
	private OfferingService offeringService;

	@GetMapping("/salon/{salonId}")
	public ResponseEntity<Set<Offering>> getServiceOfferingBySalonId(@PathVariable Long salonId,
			@RequestParam(required = false) Long categoryId) {
		var offering = offeringService.getAllOfferingBySalonId(salonId, categoryId);
		return ResponseEntity.ok(offering);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Offering> getServiceOfferingById(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(offeringService.getOfferingById(id));

	}

	@GetMapping("/list/{ids}")
	public ResponseEntity<Set<Offering>> getServiceOfferingByIds(@PathVariable Set<Long> ids) {
		return ResponseEntity.ok(offeringService.getOfferingByIds(ids));
	}
}
