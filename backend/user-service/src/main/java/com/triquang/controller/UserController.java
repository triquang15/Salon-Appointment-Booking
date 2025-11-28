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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.User;
import com.triquang.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws Exception {
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws Exception {
		return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws Exception {
		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) throws Exception {
		return new ResponseEntity<>(userService.getUserInfo(token), HttpStatus.OK);
	}
}
