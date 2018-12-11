package com.meetings.conferent.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.Room;
import com.meetings.conferent.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@GetMapping("/rooms")
	List<Room> allRoomsForSite(@RequestParam long siteId) {
		return roomService.getRoomsForSite(siteId);
	}

	@PostMapping("/rooms")
	void newRoom(@RequestBody Room newRoom) {
		roomService.insert(newRoom);
	}

	@PutMapping("/rooms")
	void updateRoom(@RequestBody Room room) {
		roomService.update(room);
	}

	@DeleteMapping("/rooms")
	void deleteRoom(@RequestParam long roomId) {
		roomService.delete(roomId);
	}
}
