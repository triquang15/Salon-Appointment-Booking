package com.triquang.service;

import java.util.Set;

import com.triquang.modal.Offering;
import com.triquang.payload.CategoryDto;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;

public interface OfferingService {
	Offering createServiceOffering(SalonDto salonDto, OfferingDto offeringDto, CategoryDto categoryDto);

	Offering updateServiceOffering(Long offeringId, Offering offering) throws Exception;

	Set<Offering> getAllOfferingBySalonId(Long salonId, Long categoryId);

	Set<Offering> getOfferingByIds(Set<Long> ids);
	
	Offering getOfferingById(Long id) throws Exception;

}
