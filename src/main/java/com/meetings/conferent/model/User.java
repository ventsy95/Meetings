package com.meetings.conferent.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER", catalog = "booking", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "username") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, updatable = false)
	private long userId;

	@Column(name = "firstName", nullable = false, length = 60)
	private String firstName;

	@Column(name = "lastName", nullable = false, length = 60)
	private String lastName;

	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	
	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "enabled", nullable = false)
	@JsonIgnore
	private boolean enabled;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	public User() {
	}

	public User(long userId, String firstName, String lastName, String username, String password, boolean enabled) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(long userId, String firstName, String lastName, String username, String password, boolean enabled,
			Set<UserRole> userRole) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return this.firstName + ", " + this.lastName + ", " + this.userId + ", " + this.username + ", " + this.password
				+ ", " + this.userRole.toArray(new UserRole[0]);
	}
}
