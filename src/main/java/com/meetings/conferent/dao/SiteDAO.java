package com.meetings.conferent.dao;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;

@Repository
public class SiteDAO implements ISiteDAO {
	
	private Session currentSession;
    private Transaction currentTransaction;
    
    public SiteDAO(){}

    public Session openCurrentSession() {
        currentSession = getSessionFactoryInstance().openSession();
        return  currentSession;
    }
    public Session openCurrentSessionWithTransaction(){
        currentSession = getSessionFactoryInstance().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
    public void closeCurrentSession(){
        currentSession.close();
    }
    public void closeCurrentSessionWithTransaction(){
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession(){
        return currentSession;
    }

    public void setCurrentSession(Session currentSession){
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction(){
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction){
        this.currentTransaction = currentTransaction;
    }

	@Override
	public void addSite(Site site) {
		getCurrentSession().save(site);
	}

	@Override
	public void updateSite(Site site) {
		getCurrentSession().update(site);
	}

	@Override
	public void deleteSite(Site site) {
		getCurrentSession().delete(site);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> getAllSites() {
		return getCurrentSession().createQuery("from Site").list();
	}

	@Override
	public Site findSiteById(long id) {
		return (Site) getCurrentSession().get(Site.class, id);
	}

}
