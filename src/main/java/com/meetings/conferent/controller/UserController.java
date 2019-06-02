package com.meetings.conferent.controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.User;
import com.meetings.conferent.model.UserRole;
import com.meetings.conferent.service.MyUserDetailsService;

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
		} catch (UsernameNotFoundException ex) {
			return false;
		}
	}

	@PostMapping("/register")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<String> register(@RequestBody User user) {
		User existingUser = userDetailsService.getUserByUsername(user.getUsername());
		if (existingUser != null) {
			return new ResponseEntity<String>("User with this username already exists.", HttpStatus.BAD_REQUEST);
		}
		if (userDetailsService.registerUser(user)) {
			System.out.println(user);
			return new ResponseEntity<String>("User registered successfully.", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error while registering user.", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/user/{username}")
	public Object getUserByUsername(@PathVariable String username) {
		User user = userDetailsService.getUserByUsername(username);
		return user;
	}

	@GetMapping("/users")
	public Object getUsers(@RequestHeader("Authorization") String auth) {
		byte[] encodedCredsBytes = auth.split(" ")[1].getBytes();
		byte[] decodedCredsBytes = Base64.getDecoder().decode(encodedCredsBytes);
		String decodedCreds = new String(decodedCredsBytes);
		String[] credentials = decodedCreds.split(":");
		User authUser = userDetailsService.getUserByUsername(credentials[0]);
		List<UserRole> roleList = authUser.getUserRole().stream()
				.filter(role -> role.getRole().equalsIgnoreCase("admin")).collect(Collectors.toList());
		if (roleList.size() > 0) {
			return userDetailsService.getUsers().stream().filter(user -> {
				for (UserRole role : user.getUserRole()) {
					if (role.getRole().equalsIgnoreCase("admin")) {
						return false;
					}
				}
				return true;
			}).collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@DeleteMapping("/users/{userId}")
	public Object deleteUser(@RequestHeader("Authorization") String auth, @PathVariable long userId) {
		byte[] encodedCredsBytes = auth.split(" ")[1].getBytes();
		byte[] decodedCredsBytes = Base64.getDecoder().decode(encodedCredsBytes);
		String decodedCreds = new String(decodedCredsBytes);
		String[] credentials = decodedCreds.split(":");
		User user = userDetailsService.getUserByUsername(credentials[0]);
		List<UserRole> roleList = user.getUserRole().stream().filter(role -> role.getRole().equalsIgnoreCase("admin"))
				.collect(Collectors.toList());
		if (roleList.size() > 0) {
			return userDetailsService.deleteUser(userId);
		} else {
			return HttpStatus.UNAUTHORIZED;
		}
	}
}
