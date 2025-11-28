package com.triquang.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.triquang.exception.UserException;
import com.triquang.modal.User;
import com.triquang.payload.KeyCloakUserDto;
import com.triquang.repository.UserRepository;
import com.triquang.service.KeyCloakService;
import com.triquang.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final KeyCloakService cloakService;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) throws UserException {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent())
			return optional.get();

		throw new UserException("User not found with ID " + id);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) throws UserException {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty()) {
			throw new UserException("User not exits with ID " + id);
		}

		userRepository.deleteById(id);

	}

	@Override
	public User updateUser(Long id, User user) throws UserException {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty()) {
			throw new UserException("User not found with ID " + id);
		}
		var existingUser = optional.get();
		existingUser.setFullName(user.getFullName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPhone(user.getPhone());
		existingUser.setRole(user.getRole());

		return userRepository.save(existingUser);
	}

	@Override
	public User getUserInfo(String token) throws Exception {
		KeyCloakUserDto cloakUserDto = cloakService.fetchUserProfile(token);
		return userRepository.findByEmail(cloakUserDto.getEmail());
	}

}
