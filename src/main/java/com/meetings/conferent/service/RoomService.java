package com.meetings.conferent.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetings.conferent.dao.MeetingDAO;
import com.meetings.conferent.dao.RoomDAO;
import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;

@Service
public class RoomService {
	@Autowired
	private RoomDAO roomDao;

	public RoomService() {
	}

	public void update(Room room) {
		roomDao.openCurrentSessionWithTransaction();
		roomDao.updateRoom(room);
		roomDao.closeCurrentSessionWithTransaction();
	}
	
	public void updateRoomOccupationById(long id, boolean isPicked) {
		roomDao.openCurrentSessionWithTransaction();
		roomDao.updateRoomOccupationById(id, isPicked);
		roomDao.closeCurrentSessionWithTransaction();
	}

	public void insert(Room room) {
		roomDao.openCurrentSessionWithTransaction();
		roomDao.addRoom(room);
		roomDao.closeCurrentSessionWithTransaction();
	}

	public Room findById(long id) {
		roomDao.openCurrentSession();
		Room room = roomDao.findRoomById(id);
		roomDao.closeCurrentSession();
		return room;
	}

	public void delete(long id) {
		Room entity = findById(id);
		roomDao.openCurrentSessionWithTransaction();
		roomDao.deleteRoom(entity);
		roomDao.closeCurrentSessionWithTransaction();
	}

	public List<Room> getRoomsForSite(long siteId) {
		roomDao.openCurrentSession();
		List<Room> rooms = roomDao.getRoomsForSite(siteId);
		roomDao.closeCurrentSession();
		return rooms;
	}
}
