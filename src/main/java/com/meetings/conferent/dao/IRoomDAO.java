package com.meetings.conferent.dao;

import java.util.List;

import com.meetings.conferent.model.Room;

public interface IRoomDAO {
	void addRoom(Room room);
	void updateRoom(Room room);
	void deleteRoom(Room room);
	void updateRoomOccupationById(long id, boolean isOccupied);
	Room findRoomById(long id);
	List<Room> getRoomsForSite(long siteId);
}
