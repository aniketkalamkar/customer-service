package com.omnirio.assignment.customerservice.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omnirio.assignment.customerservice.domain.User;
import com.omnirio.assignment.customerservice.repository.UserRepository;

@Service
public class MyDatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userrepo;
	
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     User user = userrepo.findByUserName(username);  
	     List<SimpleGrantedAuthority> grantedAuthorities =Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().getRoleType().getCode()));
	     return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), grantedAuthorities); // (2)
	  }
}