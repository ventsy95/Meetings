package com.meetings.conferent.dao;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.Meeting;

@Repository
public class MeetingDAO implements IMeetingDAO {
	
	private Session currentSession;
    private Transaction currentTransaction;
    
    public MeetingDAO(){}

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
	public void addMeeting(Meeting meeting) {
		getCurrentSession().save(meeting);
	}

	@Override
	public void updateMeeting(long id, Date startDate, Date endDate) {
		Query query = getCurrentSession().createQuery("update Meeting set startDate = :startDate, endDate = :endDate" +
				" where id = :id");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("id", id);
		int result = query.executeUpdate();
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		getCurrentSession().delete(meeting);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> getMeetingsForRoom(long roomId) {
		return getCurrentSession().createQuery("from Meeting where roomId="+roomId).list();
	}

	@Override
	public Meeting findMeetingById(long id) {
		return (Meeting) getCurrentSession().get(Meeting.class, id);
	}

}
