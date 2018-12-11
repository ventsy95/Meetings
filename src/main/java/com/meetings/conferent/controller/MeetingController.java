package com.meetings.conferent.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetService;

	@GetMapping("/")
	CsrfToken token(HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		return token;
	}
	
	@GetMapping("/meetings")
	List<Meeting> allMeetings(@RequestParam long roomId) {
		return meetService.getMeetingsForRoom(roomId);
	}

	@PostMapping("/meetings")
	Object newMeeting(@RequestBody Meeting newMeeting) {
		boolean result = meetService.insert(newMeeting);
		if(result) {
			return newMeeting;
		}else {
			return "Timeslot is taken";
		}
	}

	@PutMapping("/meetings")
	Object updateMeeting(@RequestBody Meeting meeting) {
		boolean result = meetService.update(meeting);
		if(result) {
			return meeting;
		}
		return "Timeslot is taken";
	}

	@DeleteMapping("/meetings")
	void deleteMeeting(@RequestParam long meetingId) {
		meetService.delete(meetingId);
	}
}
