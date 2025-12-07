package com.triquang.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.payload.SalonDto;
import com.triquang.payload.SalonMapper;
import com.triquang.service.SalonService;
import com.triquang.service.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

	private final SalonService salonService;
	private final UserFeignClient feignClient;

	@PostMapping
	public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto salonDto, @RequestHeader("Authorization") String token) throws Exception {
		var userDto = feignClient.getUserInfo(token).getBody();
		var salon = salonService.createSalon(salonDto, userDto);
		
		var createdSalonDto = SalonMapper.toDto(salon);
		return ResponseEntity.ok(createdSalonDto);

	}

	@PutMapping("/{salonId}")
	public ResponseEntity<SalonDto> updateSalon(@RequestBody SalonDto salonDto, @PathVariable Long salonId, @RequestHeader("Authorization") String token)
			throws Exception {
		var userDto = feignClient.getUserInfo(token).getBody();

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

	@GetMapping("/owner")
	public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@RequestHeader("Authorization") String token) throws Exception {
		var userDto = feignClient.getUserInfo(token).getBody();
		if(userDto == null) {
			throw new Exception("User not found from token");
		}
		var salons = salonService.getSalonsByOwnerId(userDto.getId());
		List<SalonDto> salonDtos = salons.stream().map(SalonMapper::toDto).toList();
		return ResponseEntity.ok(salonDtos);
	}

}
