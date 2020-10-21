package com.omnirio.assignment.customerservice.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.omnirio.assignment.customerservice.domain.types.Gender;

import lombok.Data;

@Data
@Entity(name = "user")
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
	private String userId;
	@Column(unique = true)
	private String userName;
	private LocalDate dateOfBirth;
	
	@Column(nullable = false)
	private Gender gender;
	
	private String phoneNo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId")
	private Role role;
	
	private String password;
}