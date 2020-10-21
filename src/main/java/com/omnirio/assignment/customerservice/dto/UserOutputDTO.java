package com.omnirio.assignment.customerservice.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.omnirio.assignment.customerservice.domain.types.Gender;

import lombok.Data;
@Data
public class UserOutputDTO extends RepresentationModel<UserOutputDTO> {

		private String userId;
		private String userName;
		private LocalDate dateOfBirth;
		private Gender gender;
		private String phoneNo;	
		private RoleDTO role;
}