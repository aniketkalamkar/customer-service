package com.omnirio.assignment.customerservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omnirio.assignment.customerservice.domain.User;
import com.omnirio.assignment.customerservice.dto.UserDTO;
import com.omnirio.assignment.customerservice.dto.UserOutputDTO;
import com.omnirio.assignment.customerservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/{userId}")
	public ResponseEntity<UserOutputDTO> findUserById(@PathVariable String userId) {
		Optional<User> user = userService.findUser(userId);
		UserOutputDTO userOutput = null;
		if (user.isPresent())
			userOutput = convertToUserDTO(user.get());
		else
			return new ResponseEntity<UserOutputDTO>(HttpStatus.NOT_FOUND);

		userOutput.add(linkTo(methodOn(UserController.class).findUserById(userId)).withSelfRel());
		return new ResponseEntity<UserOutputDTO>(userOutput, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<UserOutputDTO> createUser(@RequestBody UserDTO userdto) {
		User user = userService.createUser(userdto);
		UserOutputDTO userOutput = convertToUserDTO(user);
		userOutput.add(linkTo(methodOn(UserController.class).findUserById(user.getUserId())).withSelfRel());
		return new ResponseEntity<UserOutputDTO>(userOutput, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserOutputDTO> updateUser(@RequestBody UserDTO userdto, @PathVariable String userId) {
		User user = userService.updateUser(userId, userdto);
		UserOutputDTO userOutput = null;
		if (user != null)
			userOutput = convertToUserDTO(user);
		else
			return new ResponseEntity<UserOutputDTO>(HttpStatus.NOT_FOUND);

		userOutput.add(linkTo(methodOn(UserController.class).findUserById(userId)).withSelfRel());
		return new ResponseEntity<UserOutputDTO>(userOutput, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	private UserOutputDTO convertToUserDTO(User user) {
		UserOutputDTO userDTO = modelMapper.map(user, UserOutputDTO.class);
		return userDTO;
	}

	@Secured("ROLE_M")
	@GetMapping("/")
	public ResponseEntity<List<UserOutputDTO>> getAll() {

		List<User> users = userService.findAll();
		List<UserOutputDTO> userDTOList = users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
		return new ResponseEntity<List<UserOutputDTO>>(userDTOList, HttpStatus.OK);
	}
}
