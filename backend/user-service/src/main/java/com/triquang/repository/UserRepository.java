package com.triquang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triquang.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
