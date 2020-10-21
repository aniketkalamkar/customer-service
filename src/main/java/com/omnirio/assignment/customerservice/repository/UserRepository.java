package com.omnirio.assignment.customerservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omnirio.assignment.customerservice.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findByUserName(String username);

		
}
