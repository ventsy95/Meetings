package com.meetings.conferent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SITE", uniqueConstraints = { @UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") })
public class Site implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2196247965544609572L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private long siteId;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "location")
	private String location;

	public Site() {
		super();
	}

	public Site(long siteId, String name, String location) {
		this();
		this.siteId = siteId;
		this.name = name;
		this.location = location;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
