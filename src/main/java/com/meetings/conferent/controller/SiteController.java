package com.meetings.conferent.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meetings.conferent.model.Site;
import com.meetings.conferent.service.RoomService;
import com.meetings.conferent.service.SiteService;

@CrossOrigin
@RestController
public class SiteController {

	@Autowired
	private SiteService siteService;
	
	@GetMapping("/sites")
	List<Site> allSites() {
		return siteService.getAllSites();
	}

	@PostMapping("/sites")
	Object newSite(@RequestBody Site site) {
			siteService.insert(site);
			return site;
	}

	@PutMapping("/sites")
	Site updateSite(@RequestBody Site site) {
		siteService.update(site);
		return site;
	}

	@DeleteMapping("/sites")
	long deleteSite(@RequestParam long siteId) {
		siteService.delete(siteId);
		return siteId;
	}
}

