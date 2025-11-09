package com.triquang.service;

import java.util.List;

import com.triquang.exception.UserException;
import com.triquang.modal.User;

public interface UserService {
	User createUser(User user);
	User getUserById(Long id) throws UserException;
	List<User> getAllUsers();
	void deleteUser(Long id) throws UserException;
	User updateUser(Long id, User userDetails) throws UserException;
}
