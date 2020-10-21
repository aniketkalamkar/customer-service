package com.omnirio.assignment.customerservice.dto;

import com.omnirio.assignment.customerservice.domain.types.RoleType;

import lombok.Data;

@Data
public class RoleDTO {

	private String roleId;
	private String roleName;
	private String roleCode;
	private RoleType roleType;

}
