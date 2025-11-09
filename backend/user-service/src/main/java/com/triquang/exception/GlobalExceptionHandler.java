package com.triquang.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.triquang.payload.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());
		return ResponseEntity.ok(response);

	}
}
