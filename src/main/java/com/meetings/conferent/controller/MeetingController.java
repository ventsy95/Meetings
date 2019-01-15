package com.meetings.conferent.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;
import com.meetings.conferent.service.MeetingService;
import com.meetings.conferent.service.MyUserDetailsService;
import com.meetings.conferent.service.RoomService;

@CrossOrigin
@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetService;
	@Autowired
	private MyUserDetailsService userService;
	@Autowired
	private RoomService roomService;

	@GetMapping("/meetings/{roomId}")
	List<Meeting> allMeetings(@PathVariable long roomId) {
		return meetService.getMeetingsForRoom(roomId);
	}

	@PostMapping("/meetings")
	@ResponseStatus(value = HttpStatus.OK)
	Object newMeeting(@RequestBody Meeting newMeeting) {
		if (newMeeting != null && newMeeting.getOwner() != null && newMeeting.getOwner().getUsername() != null) {
			newMeeting.setOwner(userService.getUserByUsername(newMeeting.getOwner().getUsername()));
		}
		boolean result = meetService.insert(newMeeting);
		if (result) {
			return newMeeting;
		} else {
			return new ResponseEntity<>(HttpStatus.IM_USED);
		}
	}

	@PutMapping("/meetings")
	Object updateMeeting(@RequestBody Meeting meeting) {
		boolean result = meetService.update(meeting);
		if (result) {
			return meeting;
		}
		return new ResponseEntity<>(HttpStatus.IM_USED);
	}
	
	@PutMapping("/meetings/{meetingId}")
	Object updateMeetingStatus(@PathVariable long meetingId, @RequestBody boolean isStarted) {
		boolean result = meetService.updateMeetingStatus(meetingId, isStarted);
		if (result) {
			return meetingId;
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/meetings/{meetingId}")
	long deleteMeeting(@PathVariable long meetingId) {
		meetService.delete(meetingId);
		return meetingId;
	}
}
