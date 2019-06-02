package com.meetings.conferent.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MEETING", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Meeting implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3656115752861898205L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, updatable = false, nullable = false)
	private long meetingId;
	
	@Column(name = "title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;

	@Column(name = "isStarted")
	private boolean isStarted;
	
	@Column(name = "startDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name = "endDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name = "ownerId")
	private User owner;
	
	public Meeting() {
		super();
	}

	public Meeting(long meetingId, String title, Room room, boolean isStarted, Date startDate, Date endDate,
			User owner) {
		this();
		this.meetingId = meetingId;
		this.title = title;
		this.room = room;
		this.isStarted = isStarted;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
	}

	public long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(long meetingId) {
		this.meetingId = meetingId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
