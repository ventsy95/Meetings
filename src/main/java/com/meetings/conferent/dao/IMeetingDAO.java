package com.meetings.conferent.dao;

import java.util.Date;
import java.util.List;

import com.meetings.conferent.model.Meeting;

public interface IMeetingDAO {
	void addMeeting(Meeting meeting);
	void updateMeeting(long id, Date startDate, Date endDate);
	void deleteMeeting(Meeting meeting);
	Meeting findMeetingById(long id);
	List<Meeting> getMeetingsForRoom(long roomId);
}
