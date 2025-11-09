package com.triquang.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.payload.SalonDto;
import com.triquang.payload.SalonMapper;
import com.triquang.payload.UserDto;
import com.triquang.service.SalonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

	private final SalonService salonService;

	@PostMapping
	public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto salonDto) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);

		var salon = salonService.createSalon(salonDto, userDto);
		SalonDto createdSalonDto = SalonMapper.toDto(salon);
		return ResponseEntity.ok(createdSalonDto);

	}

	@PutMapping("/{salonId}")
	public ResponseEntity<SalonDto> updateSalon(@RequestBody SalonDto salonDto, @PathVariable Long salonId)
			throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(1L);

		var salon = salonService.updateSalon(salonDto, userDto, salonId);
		return ResponseEntity.ok(SalonMapper.toDto(salon));
	}

	@GetMapping("/{salonId}")
	public ResponseEntity<SalonDto> getSalonById(@PathVariable Long salonId) throws Exception {
		var salon = salonService.getSalonById(salonId);
		return ResponseEntity.ok(SalonMapper.toDto(salon));
	}

	@GetMapping
	public ResponseEntity<List<SalonDto>> getAllSalons() {
		var salons = salonService.getAllSalons();
		var salonDtos = salons.stream().map(SalonMapper::toDto).toList();
		return ResponseEntity.ok(salonDtos);
	}

	@GetMapping("/search")
	public ResponseEntity<List<SalonDto>> searchSalonsByCity(@RequestParam String city) {
		var salons = salonService.searchSalonsByCity(city);
		var salonDtos = salons.stream().map(SalonMapper::toDto).toList();
		return ResponseEntity.ok(salonDtos);
	}

	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@PathVariable Long ownerId) {
		var salons = salonService.getSalonsByOwnerId(ownerId);
		List<SalonDto> salonDtos = salons.stream().map(SalonMapper::toDto).toList();
		return ResponseEntity.ok(salonDtos);
	}

}
