package com.meetings.conferent.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meetings.conferent.dao.UserDAO;
import com.meetings.conferent.model.UserRole;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		userDao.openCurrentSessionWithTransaction();
		com.meetings.conferent.model.User user = userDao.findByUserName(username);
		if (user != null) {
			List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
			userDao.closeCurrentSessionWithTransaction();
			return buildUserForAuthentication(user, authorities);
		} else {
			userDao.closeCurrentSessionWithTransaction();
			throw new UsernameNotFoundException("User with such username does not exist.");
		}
	}
	
	public com.meetings.conferent.model.User getUserByUsername(final String username) {
		userDao.openCurrentSessionWithTransaction();
		com.meetings.conferent.model.User user = userDao.findByUserName(username);
		userDao.closeCurrentSessionWithTransaction();
		if (user != null) {
			return user;
		}else {
			return null;
		}
	}

	private User buildUserForAuthentication(com.meetings.conferent.model.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

		return result;
	}
}
