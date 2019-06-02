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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.meetings.conferent.dao.MeetingDAO;
import com.meetings.conferent.dao.UserDAO;
import com.meetings.conferent.model.UserRole;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private MeetingDAO meetingDao;

	public boolean registerUser(com.meetings.conferent.model.User user) {
		if (getUserByUsername(user.getUsername()) == null) {
			userDao.openCurrentSessionWithTransaction();
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setEnabled(true);
			userDao.addUser(user);
			Set<UserRole> userRoles = new HashSet<UserRole>();
			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole("USER");
			userRoles.add(role);
			userDao.addUserRole(role);
			user.setUserRole(userRoles);
			userDao.updateUser(user);
			userDao.closeCurrentSessionWithTransaction();
			return true;
		}
		return false;
	}

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
		} else {
			return null;
		}
	}

	public List<com.meetings.conferent.model.User> getUsers() {
		userDao.openCurrentSessionWithTransaction();
		List<com.meetings.conferent.model.User> users = userDao.getUsers();
		userDao.closeCurrentSessionWithTransaction();
		return users;
	}

	public com.meetings.conferent.model.User deleteUser(long userId) {
		userDao.openCurrentSessionWithTransaction();
		com.meetings.conferent.model.User user = userDao.findById(userId);
		meetingDao.deleteMeetingsForUser(userId);
		userDao.deleteUser(user);
		userDao.closeCurrentSessionWithTransaction();
		return user;
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
