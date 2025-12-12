package com.triquang.service.client;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.triquang.payload.OfferingDto;

@FeignClient("OFFERING-SERVICE")
public interface OfferingFeignClient {

	@GetMapping("/api/service-offering/list/{ids}")
	public ResponseEntity<Set<OfferingDto>> getServiceOfferingByIds(@PathVariable Set<Long> ids);
}
