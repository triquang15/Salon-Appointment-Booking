package com.triquang.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();

		// If you use cookies / Authorization header
		config.setAllowCredentials(true);

		// Allowed origins (FE domains)
		config.setAllowedOriginPatterns(
				Arrays.asList("http://localhost:3000", "http://localhost:4200", "https://your-domain.com"));

		// Allowed HTTP methods
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

		// Allowed headers
		config.setAllowedHeaders(
				Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));

		// Exposed headers (if FE needs them)
		config.setExposedHeaders(Arrays.asList("Authorization"));

		// Cache preflight response
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
