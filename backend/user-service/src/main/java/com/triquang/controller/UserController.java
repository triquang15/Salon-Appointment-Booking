package com.triquang.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.User;
import com.triquang.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/api/users")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/api/users/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws Exception {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@PutMapping("/api/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {
		return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
	}

	@DeleteMapping("/api/users/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws Exception {
		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
	}
}
