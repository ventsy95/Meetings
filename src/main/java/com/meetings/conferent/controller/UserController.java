package com.meetings.conferent.controller;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.User;
import com.meetings.conferent.service.MyUserDetailsService;
import com.meetings.conferent.utils.EncriptionUtil;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@PostMapping("/login")
	public boolean login(@RequestBody User user) throws Exception {
		try {
			UserDetails details = userDetailsService.loadUserByUsername(user.getUsername());
			return user.getUsername().equals(details.getUsername()) && EncriptionUtil.encrypt(user.getPassword()).equals(details.getPassword());
		} catch(UsernameNotFoundException ex) {
			return false;
		}
	}
	
	@RequestMapping("/user")
	public Principal user(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
		return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
	}
}
