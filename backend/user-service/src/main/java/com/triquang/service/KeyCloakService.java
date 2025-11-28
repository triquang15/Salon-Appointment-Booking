package com.triquang.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.triquang.payload.Credential;
import com.triquang.payload.KeyCloakRole;
import com.triquang.payload.KeyCloakUserDto;
import com.triquang.payload.SignupDto;
import com.triquang.payload.TokenResponse;
import com.triquang.payload.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeyCloakService {
	private static final String KEYCLOAK_URL = "http://localhost:8080";
	private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_URL + "/admin/realms/master/users";
	private static final String TOKEN_URL = KEYCLOAK_URL + "/realms/master/protocol/openid-connect/token";

	private final static String CLIENT_ID = "salon-booking-client"; // client id for salon-booking-client
	private final static String CLIENT_SECRET = "SeKG8AV424fxOjm9ACyJJHKDbBIpnxYH";

	private final static String GRANT_TYPE = "password";
	private final static String scope = "openid profile email";
	private final static String ADMIN_USERNAME = "triquang95";
	private final static String ADMIN_PASSWORD = "12345678";

	private final static String CLIENT_ID_ADMIN = "6b3b33e4-c86a-4a7f-b3e0-0faa8e689321"; // client id for admin-cli

	private final RestTemplate restTemplate;

	public void createUser(SignupDto signupDto) throws Exception {

		var accessToken = getAdminAccessToken(ADMIN_USERNAME, ADMIN_PASSWORD, GRANT_TYPE, null).getAccessToken();
		var credential = new Credential();
		credential.setType("password");
		credential.setTemporary(false);
		credential.setValue(signupDto.getPassword());

		var userRequest = new UserRequest();
		userRequest.setUsername(signupDto.getUsername());
		userRequest.setFirstName(signupDto.getFullName());
		userRequest.setEmail(signupDto.getEmail());
		userRequest.setEnabled(true);
		userRequest.getCredentials().add(credential);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.setBearerAuth(accessToken);

		HttpEntity<UserRequest> request = new HttpEntity<>(userRequest, headers);
		ResponseEntity<String> response = restTemplate.exchange(KEYCLOAK_ADMIN_API, HttpMethod.POST, request,
				String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("User created successfully in Keycloak");

			KeyCloakUserDto cloakUserDto = getUserByUsername(signupDto.getUsername(), accessToken);
			KeyCloakRole role = getRoleByName(CLIENT_ID_ADMIN, signupDto.getRole().name(), accessToken);

			assignRoleToUser(cloakUserDto.getId(), CLIENT_ID_ADMIN, List.of(role), accessToken);
		} else {
			throw new Exception("Failed to create user in Keycloak: " + response.getBody());
		}
	}

	public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken)
			throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("client_id", CLIENT_ID);
		requestBody.add("client_secret", CLIENT_SECRET);
		requestBody.add("grant_type", grantType);
		requestBody.add("scope", scope);
		requestBody.add("username", username);
		requestBody.add("password", password);
		requestBody.add("refresh_token", refreshToken);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		try {

			ResponseEntity<TokenResponse> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity,
					TokenResponse.class);
			return response.getBody();
		} catch (Exception e) {

			System.err.println("Client error: " + e.getMessage());
			throw new Exception(e.getMessage());

		}

	}

	public KeyCloakRole getRoleByName(String clientId, String roleName, String accessToken) {
		String TOKEN_URL = KEYCLOAK_URL + "/admin/realms/master/clients/" + clientId + "/roles/" + roleName;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		var reponse = restTemplate.exchange(TOKEN_URL, HttpMethod.GET, new HttpEntity<>(headers), KeyCloakRole.class);

		if (reponse.getStatusCode().is2xxSuccessful()) {
			return reponse.getBody();
		}

		throw new RuntimeException("Failed to get role from Keycloak");
	}

	public KeyCloakUserDto getUserByUsername(String username, String accessToken) {
		String TOKEN_URL = KEYCLOAK_URL + "/admin/realms/master/users?username=" + username;
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		var reponse = restTemplate.exchange(TOKEN_URL, HttpMethod.GET, new HttpEntity<>(headers),
				KeyCloakUserDto[].class);

		if (reponse.getStatusCode().is2xxSuccessful() && reponse.getBody() != null && reponse.getBody().length > 0) {
			return reponse.getBody()[0];
		}
		throw new RuntimeException("Failed to get user from Keycloak");
	}

	public void assignRoleToUser(String userId, String clientId, List<KeyCloakRole> role, String accessToken) {
		String TOKEN_URL = KEYCLOAK_URL + "/admin/realms/master/users/" + userId + "/role-mappings/clients/" + clientId;

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<List<KeyCloakRole>> request = new HttpEntity<>(role, headers);
		var response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Void.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Role assigned successfully to user in Keycloak");
		} else {
			throw new RuntimeException("Failed to assign role to user in Keycloak");
		}
	}
	
	public KeyCloakUserDto fetchUserProfile(String accessToken) throws Exception {
	    String TOKEN_URL = KEYCLOAK_URL + "/realms/master/protocol/openid-connect/userinfo";

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", accessToken);
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<Void> request = new HttpEntity<>(headers);

	    try {
	        var response = restTemplate.exchange(
	                TOKEN_URL,
	                HttpMethod.GET,
	                request,
	                KeyCloakUserDto.class);

	        return response.getBody();

	    } catch (HttpStatusCodeException ex) {
	        System.err.println("Keycloak error: " + ex.getResponseBodyAsString());
	        throw new Exception("Failed to get user info: " + ex.getStatusCode(), ex);

	    } catch (Exception ex) {
	        throw new Exception("Unexpected error while fetching Keycloak user info", ex);
	    }
	}
}
