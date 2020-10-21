package com.omnirio.assignment.customerservice.service;

import java.util.List;
import java.util.Optional;

import com.omnirio.assignment.customerservice.domain.User;
import com.omnirio.assignment.customerservice.dto.UserDTO;

public interface UserService {

	public User createUser(UserDTO user);
	
	public User updateUser(String userId, UserDTO user);
	
	public void deleteUser(String userId);
	
	public Optional<User> findUser(String userId);

	public List<User> findAll();
}
