package com.meetings.conferent.dao;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;

@Repository
public class RoomDAO implements IRoomDAO {
	
	private Session currentSession;
    private Transaction currentTransaction;
    
    public RoomDAO(){}

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
	public void addRoom(Room room) {
		getCurrentSession().save(room);
	}

	@Override
	public void updateRoom(Room room) {
		getCurrentSession().update(room);
	}

	@Override
	public void deleteRoom(Room room) {
		getCurrentSession().delete(room);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Room> getRoomsForSite(long siteId) {
		return getCurrentSession().createQuery("from Room where siteId= :siteId").setParameter("siteId", siteId)
				.list();
	}

	@Override
	public Room findRoomById(long id) {
		return (Room) getCurrentSession().get(Room.class, id);
	}

}
