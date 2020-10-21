package com.omnirio.assignment.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omnirio.assignment.customerservice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
