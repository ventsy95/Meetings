package com.meetings.conferent.model;

import java.io.Serializable;

import javax.persistence.*;

import com.meetings.conferent.utils.EncriptionUtil;

@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "passcode") })
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private long userId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "passcode", unique = true)
	private String passcode;
	
	@Column(name = "isAdmin")
	private boolean isAdmin;
	
	@Column(name = "company")
	private String company;

	public User() {
		super();
	}

	public User(long userId, String firstName, String lastName, String passcode, boolean isAdmin, String company) throws Exception {
		this();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passcode = EncriptionUtil.encrypt(passcode);
		this.isAdmin = isAdmin;
		this.company = company;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		try {
			this.passcode = EncriptionUtil.encrypt(passcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
