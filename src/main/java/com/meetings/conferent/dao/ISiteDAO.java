package com.meetings.conferent.dao;

import java.util.List;

import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;

public interface ISiteDAO {
	void addSite(Site site);
	void updateSite(Site site);
	void deleteSite(Site site);
	Site findSiteById(long id);
	List<Site> getAllSites();
}
