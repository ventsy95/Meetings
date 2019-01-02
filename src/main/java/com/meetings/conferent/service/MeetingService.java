package com.meetings.conferent.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetings.conferent.dao.MeetingDAO;
import com.meetings.conferent.model.Meeting;

@Service
public class MeetingService {
	@Autowired
	private MeetingDAO meetingDao;

	public MeetingService() {
	}

	public boolean update(Meeting meeting) {
		meetingDao.openCurrentSessionWithTransaction();
		if (!isTimeslotTaken(meeting)) {
			meetingDao.updateMeeting(meeting.getMeetingId(), meeting.getStartDate(), meeting.getEndDate());
			meetingDao.closeCurrentSessionWithTransaction();
			return true;
		}
		meetingDao.closeCurrentSessionWithTransaction();
		return false;
	}

	public boolean insert(Meeting meeting) {
		meetingDao.openCurrentSessionWithTransaction();
		if (!isTimeslotTaken(meeting)) {
			meetingDao.addMeeting(meeting);
			meetingDao.closeCurrentSessionWithTransaction();
			return true;
		}
		meetingDao.closeCurrentSessionWithTransaction();
		return false;
	}

	public Meeting findById(long id) {
		meetingDao.openCurrentSession();
		Meeting meeting = meetingDao.findMeetingById(id);
		meetingDao.closeCurrentSession();
		return meeting;
	}

	public void delete(long id) {
		Meeting entity = findById(id);
		meetingDao.openCurrentSessionWithTransaction();
		meetingDao.deleteMeeting(entity);
		meetingDao.closeCurrentSessionWithTransaction();
	}

	public List<Meeting> getMeetingsForRoom(long roomId) {
		meetingDao.openCurrentSession();
		List<Meeting> meetings = meetingDao.getMeetingsForRoom(roomId);
		meetingDao.closeCurrentSession();
		return meetings;
	}

	private boolean isTimeslotTaken(Meeting meeting) {
		List<Meeting> currentMeetings = meetingDao.getMeetingsForRoom(meeting.getRoom().getRoomId());
		if (meeting.getStartDate() != null && meeting.getEndDate() != null) {
			for (Meeting existMeeting : currentMeetings) {
				if (existMeeting.getStartDate() != null && existMeeting.getEndDate() != null
						&& meeting.getMeetingId() != existMeeting.getMeetingId()) {
					if (meeting.getStartDate().getTime() >= existMeeting.getStartDate().getTime()
							&& meeting.getStartDate().getTime() < existMeeting.getEndDate().getTime()) {
						return true;
					}
					if (meeting.getEndDate().getTime() > existMeeting.getStartDate().getTime()
							&& meeting.getEndDate().getTime() <= existMeeting.getEndDate().getTime()) {
						return true;
					}
					if (meeting.getStartDate().getTime() <= existMeeting.getStartDate().getTime()
							&& meeting.getEndDate().getTime() >= existMeeting.getEndDate().getTime()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
