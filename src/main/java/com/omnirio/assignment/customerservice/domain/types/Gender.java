package com.omnirio.assignment.customerservice.domain.types;

public enum Gender {

MALE("M"),FEMALE("F");
	
	private String code;

	private Gender(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}