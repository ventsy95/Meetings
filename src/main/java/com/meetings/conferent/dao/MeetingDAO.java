package com.meetings.conferent.dao;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;

@Repository
public class MeetingDAO implements IMeetingDAO {

	private Session currentSession;
	private Transaction currentTransaction;

	public MeetingDAO() {
	}

	public Session openCurrentSession() {
		currentSession = getSessionFactoryInstance().openSession();
		return currentSession;
	}

	public Session openCurrentSessionWithTransaction() {
		currentSession = getSessionFactoryInstance().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	public void closeCurrentSession() {
		currentSession.close();
	}

	public void closeCurrentSessionWithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	@Override
	public void addMeeting(Meeting meeting) {
		getCurrentSession().persist(meeting);
	}

	@Override
	public void updateMeeting(long id, Date startDate, Date endDate, boolean isStarted) {
		Query query = getCurrentSession()
				.createQuery("update Meeting set startDate = :startDate, endDate = :endDate, isStarted = :isStarted"
						+ " where id = :id");
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("isStarted", isStarted);
		query.setParameter("id", id);
		int result = query.executeUpdate();
	}

	@Override
	public void updateMeetingStatus(long id, boolean isStarted) {
		Meeting meeting = (Meeting) getCurrentSession().get(Meeting.class, id);
		if (meeting.isStarted() != isStarted) {
			meeting.setStarted(isStarted);
			getCurrentSession().update(meeting);
		}
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		getCurrentSession().delete(meeting);
	}

	@Override
	public void deleteMeetingsForUser(long userId) {
		if (getCurrentSession() != null && getCurrentSession().isOpen()) {
			getCurrentSession().createQuery("delete from Meeting where ownerId = :ownerId")
					.setParameter("ownerId", userId).executeUpdate();
		} else {
			openCurrentSession().createQuery("delete from Meeting where ownerId = :ownerId")
					.setParameter("ownerId", userId).executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meeting> getMeetingsForRoom(long roomId) {
		return getCurrentSession().createQuery("from Meeting where roomId= :roomId").setParameter("roomId", roomId)
				.list();
	}

	@Override
	public Meeting findMeetingById(long id) {
		return (Meeting) getCurrentSession().get(Meeting.class, id);
	}

}
