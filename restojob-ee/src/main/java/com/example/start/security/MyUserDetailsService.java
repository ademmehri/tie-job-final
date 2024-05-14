package com.example.start.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.start.Entities.Employee;
import com.example.start.Repositories.EmployeeRepo;
import com.example.start.Services.EmployeeService;



@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	EmployeeRepo userrep;
	
	

	@Override
	public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
		Employee user = userrep.findByemail(email);
		if (user == null)
			throw new UsernameNotFoundException("Utilisateur introuvable !");
		List<GrantedAuthority> auths = new ArrayList<>();
		user.getRoles().forEach(role -> {
			GrantedAuthority auhority = new SimpleGrantedAuthority(role.getRole());
			auths.add(auhority);
		});
		return new org.springframework.security.core.userdetails.User(	user.getEmail(), user.getPassword(), auths);
	}
}
