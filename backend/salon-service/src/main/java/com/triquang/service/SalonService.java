package com.triquang.service;

import java.util.List;

import com.triquang.modal.Salon;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;

public interface SalonService {
	Salon createSalon(SalonDto salonDto, UserDto userDto);
	Salon updateSalon(SalonDto salonDto, UserDto userDto, Long salonId) throws Exception;
	List<Salon> getAllSalons();
	Salon getSalonById(Long salonId) throws Exception;
	List<Salon> getSalonsByOwnerId(Long ownerId);
	List<Salon> searchSalonsByCity(String city);
}
