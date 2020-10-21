package com.omnirio.assignment.customerservice.domain.types;

public enum RoleType {

	CUSTOMER("C"),MANAGER("M");

	private String code;

	private RoleType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
