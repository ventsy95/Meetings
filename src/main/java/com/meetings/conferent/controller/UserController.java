package com.meetings.conferent.controller;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.User;
import com.meetings.conferent.service.MyUserDetailsService;
import com.meetings.conferent.utils.EncriptionUtil;

@CrossOrigin
@RestController
public class UserController {
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public Object login(@RequestHeader("Authorization") String auth) throws Exception {
		try {
			byte[] encodedCredsBytes = auth.split(" ")[1].getBytes();
			byte[] decodedCredsBytes = Base64.getDecoder().decode(encodedCredsBytes);
			String decodedCreds = new String(decodedCredsBytes);
			String[] credentials = decodedCreds.split(":");
			UserDetails details = userDetailsService.loadUserByUsername(credentials[0]);
			return details.getAuthorities();
		} catch(UsernameNotFoundException ex) {
			return false;
		}
	}
	
	@GetMapping("/user/{username}")
	public Object getUserByUsername(@PathVariable String username) {
		User user = userDetailsService.getUserByUsername(username);
		return user;
	}
}
