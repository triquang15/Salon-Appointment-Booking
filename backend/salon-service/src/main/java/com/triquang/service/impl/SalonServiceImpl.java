package com.triquang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.triquang.modal.Salon;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;
import com.triquang.repository.SalonRepository;
import com.triquang.service.SalonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

	private final SalonRepository salonRepository;

	@Override
	public Salon createSalon(SalonDto salonDto, UserDto userDto) {
		Salon salon = new Salon();
		salon.setName(salonDto.getName());
		salon.setEmail(salonDto.getEmail());
		salon.setImages(salonDto.getImages());
		salon.setAddress(salonDto.getAddress());
		salon.setPhoneNumber(salonDto.getPhoneNumber());
		salon.setCity(salonDto.getCity());
		salon.setOwnerId(userDto.getId());
		salon.setOpeningTime(salonDto.getOpeningTime());
		salon.setClosingTime(salonDto.getClosingTime());

		return salonRepository.save(salon);
	}

	@SuppressWarnings("null")
	@Override
	public Salon updateSalon(SalonDto salonDto, UserDto userDto, Long salonId) throws Exception {
		var salon = salonRepository.findById(salonId).orElseThrow(() -> new Exception("Salon not found"));
		// Update fields
		if (salon != null && !salon.getOwnerId().equals(userDto.getId())) {
			throw new Exception("You are not authorized to update this salon");
		}
		salon.setName(salonDto.getName());
		salon.setEmail(salonDto.getEmail());
		salon.setImages(salonDto.getImages());
		salon.setAddress(salonDto.getAddress());
		salon.setPhoneNumber(salonDto.getPhoneNumber());
		salon.setCity(salonDto.getCity());
		salon.setOpeningTime(salonDto.getOpeningTime());
		salon.setClosingTime(salonDto.getClosingTime());
		salon.setOwnerId(userDto.getId());

		return salonRepository.save(salon);
	}

	@Override
	public List<Salon> getAllSalons() {
		return salonRepository.findAll();
	}

	@Override
	public Salon getSalonById(Long salonId) throws Exception {
		var salon = salonRepository.findById(salonId).orElse(null);
		if (salon == null) {
			throw new Exception("Salon not found with id: " + salonId);
		}

		return salon;
	}

	public List<Salon> getSalonsByOwnerId(Long ownerId) {
	    return salonRepository.findByOwnerId(ownerId);
	}

	@Override
	public List<Salon> searchSalonsByCity(String city) {
		return salonRepository.searchSalonsByCity(city);
	}

}
