package com.meetings.conferent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.Room;
import com.meetings.conferent.service.RoomService;

@CrossOrigin
@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;

	@GetMapping("/rooms/{siteId}")
	List<Room> allRoomsForSite(@PathVariable long siteId) {
		return roomService.getRoomsForSite(siteId);
	}

	@GetMapping("/room/{roomId}")
	Room getRoomById(@PathVariable long roomId) {
		return roomService.findById(roomId);
	}

	@PostMapping("/rooms")
	Object newRoom(@RequestBody Room newRoom) {
		roomService.insert(newRoom);
		return newRoom;
	}

	@PutMapping("/rooms")
	Room updateRoom(@RequestBody Room room) {
		roomService.update(room);
		return room;
	}

	@PutMapping("/rooms/{roomId}")
	long updateRoomOccupationById(@PathVariable long roomId, @RequestBody boolean isPicked) {
		roomService.updateRoomOccupationById(roomId, isPicked);
		return roomId;
	}

	@DeleteMapping("/rooms/{roomId}")
	long deleteRoom(@PathVariable long roomId) {
		roomService.delete(roomId);
		return roomId;
	}
}
