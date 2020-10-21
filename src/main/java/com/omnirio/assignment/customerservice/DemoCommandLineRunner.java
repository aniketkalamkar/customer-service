//package com.omnirio.assignment.customerservice;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.omnirio.assignment.customerservice.domain.Role;
//import com.omnirio.assignment.customerservice.domain.User;
//import com.omnirio.assignment.customerservice.domain.types.Gender;
//import com.omnirio.assignment.customerservice.domain.types.RoleType;
//import com.omnirio.assignment.customerservice.repository.RoleRepository;
//import com.omnirio.assignment.customerservice.repository.UserRepository;
//
//@Component
//class DemoCommandLineRunner implements CommandLineRunner{
//
//	
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		Role role = new Role();
//		role.setRoleId("1");
//		
//		role.setRoleCode("role");
//		role.setRoleName("name1");
//		role.setRoleType(RoleType.CUSTOMER);
//		
//		roleRepository.save(role);
//		
//		role.setRoleId("2");
//		role.setRoleCode("role2");
//		role.setRoleName("name2");
//		role.setRoleType(RoleType.MANAGER);
//		role=roleRepository.save(role);
//		
//		
//		User user = new User();
//		user.setUserName("application-user");
//		user.setPassword(passwordEncoder.encode("password"));
//		user.setGender(Gender.FEMALE);
//		Role role1= roleRepository.getOne(role.getRoleId());
//		
//		user.setRole(role1);
//
//		userRepository.save(user);
//	}
//}