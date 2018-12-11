package com.meetings.conferent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ROOM", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "name") })
public class Room implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private long roomId;

	@Column(name = "name", unique = true)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "siteId")
	private Site site;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "isPicked")
	private boolean isPicked;
	
	public Room() {
		super();
	}

	public Room(long roomId, String name, Site site, String location, boolean isPicked) {
		this();
		this.roomId = roomId;
		this.name = name;
		this.site = site;
		this.location = location;
		this.isPicked = isPicked;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isPicked() {
		return isPicked;
	}

	public void setPicked(boolean isPicked) {
		this.isPicked = isPicked;
	}
}
