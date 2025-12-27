package com.triquang.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;
import java.util.Map;

public class JwtUtil {

    @SuppressWarnings("unchecked")
    public static String extractUserRole(String token) {
        DecodedJWT jwt = JWT.decode(token);

        Map<String, Object> resourceAccess =
            jwt.getClaim("resource_access").asMap();

        if (resourceAccess == null) return null;

        Map<String, Object> client =
            (Map<String, Object>) resourceAccess.get("salon-booking-client");

        if (client == null) return null;

        List<String> roles = (List<String>) client.get("roles");

        return (roles != null && !roles.isEmpty()) ? roles.get(0) : null;
    }
}
