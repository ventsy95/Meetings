package com.meetings.conferent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetings.conferent.dao.RoomDAO;
import com.meetings.conferent.dao.SiteDAO;
import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;

@Service
public class SiteService {
	@Autowired
	private SiteDAO siteDao;

	public SiteService() {
	}

	public void update(Site site) {
		siteDao.openCurrentSessionWithTransaction();
		siteDao.updateSite(site);
		siteDao.closeCurrentSessionWithTransaction();
	}

	public void insert(Site site) {
		siteDao.openCurrentSessionWithTransaction();
		siteDao.addSite(site);
		siteDao.closeCurrentSessionWithTransaction();
	}

	public Site findById(long id) {
		siteDao.openCurrentSession();
		Site site = siteDao.findSiteById(id);
		siteDao.closeCurrentSession();
		return site;
	}

	public void delete(long id) {
		Site entity = findById(id);
		siteDao.openCurrentSessionWithTransaction();
		siteDao.deleteSite(entity);
		siteDao.closeCurrentSessionWithTransaction();
	}

	public List<Site> getAllSites() {
		siteDao.openCurrentSession();
		List<Site> sites = siteDao.getAllSites();
		siteDao.closeCurrentSession();
		return sites;
	}
}
