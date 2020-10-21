package com.omnirio.assignment.customerservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omnirio.assignment.customerservice.domain.Role;
import com.omnirio.assignment.customerservice.domain.User;
import com.omnirio.assignment.customerservice.dto.UserDTO;
import com.omnirio.assignment.customerservice.repository.RoleRepository;
import com.omnirio.assignment.customerservice.repository.UserRepository;
import com.omnirio.assignment.customerservice.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public User createUser(UserDTO userDTO) {
		
		User user = convertToUser(userDTO);
		
		Role role= roleRepository.getOne(userDTO.getRoleId());
		
		user.setRole(role);
		user.setPassword(passwordEncoder.encode("password"));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(String userId, UserDTO userDTO) {
	
		Optional<User> userOpt=userRepository.findById(userId);
		if(userOpt.isPresent()){
			User user = userOpt.get();
			user.setDateOfBirth(userDTO.getDateOfBirth());
			user.setGender(userDTO.getGender());
			user.setPhoneNo(userDTO.getPhoneNo());
			user.setUserName(userDTO.getUserName());
			Role role= roleRepository.getOne(userDTO.getRoleId());
			user.setRole(role);
			return userRepository.save(user);
		}
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public Optional<User> findUser(String userId) {
		return userRepository.findById(userId);
	}

	private User convertToUser(UserDTO userDTO) {
		User user= modelMapper.map(userDTO, User.class);
	    return user;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}


}