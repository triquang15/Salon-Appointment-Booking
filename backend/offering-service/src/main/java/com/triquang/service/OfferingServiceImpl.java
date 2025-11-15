package com.triquang.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triquang.modal.Offering;
import com.triquang.payload.CategoryDto;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;
import com.triquang.repository.OfferingRepository;

@Service
public class OfferingServiceImpl implements OfferingService {

	@Autowired
	private OfferingRepository offeringRepository;

	@Override
	public Offering createServiceOffering(SalonDto salonDto, OfferingDto offeringDto, CategoryDto categoryDto) {
		var offering = new Offering();
		offering.setImage(offeringDto.getImage());
		offering.setCategoryId(categoryDto.getId());
		offering.setSalonId(salonDto.getId());
		offering.setDescription(offeringDto.getDescription());
		offering.setName(offeringDto.getName());
		offering.setPrice(offeringDto.getPrice());
		offering.setDuration(offeringDto.getDuration());

		return offeringRepository.save(offering);
	}

	@Override
	public Offering updateServiceOffering(Long offeringId, Offering offering) throws Exception {
		Offering exitsOffering = offeringRepository.findById(offeringId).orElse(null);
		if (exitsOffering == null) {
			throw new Exception("Service offering not found with Id " + offeringId);
		}
		exitsOffering.setImage(offering.getImage());
		exitsOffering.setDescription(offering.getDescription());
		exitsOffering.setName(offering.getName());
		exitsOffering.setPrice(offering.getPrice());
		exitsOffering.setDuration(offering.getDuration());

		return offeringRepository.save(exitsOffering);
	}

	@Override
	public Set<Offering> getAllOfferingBySalonId(Long salonId, Long categoryId) {
	    Set<Offering> offerings = offeringRepository.findBySalonId(salonId);

	    if (categoryId != null) {
	        offerings = offerings.stream()
	            .filter(o -> categoryId.equals(o.getCategoryId()))
	            .collect(Collectors.toSet());
	    }

	    return offerings;
	}

	@Override
	public Set<Offering> getOfferingByIds(Set<Long> ids) {
		List<Offering> offerings = offeringRepository.findAllById(ids);
		return new HashSet<>(offerings);
	}

	@Override
	public Offering getOfferingById(Long offeringId) throws Exception {
		Offering exitsOffering = offeringRepository.findById(offeringId).orElse(null);
		if (exitsOffering == null) {
			throw new Exception("Service offering not found with Id " + offeringId);
		}
		return exitsOffering;
	}

}
