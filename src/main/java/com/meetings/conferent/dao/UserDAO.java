package com.meetings.conferent.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.User;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

@Repository
public class UserDAO implements IUserDAO {
	private Session currentSession;
    private Transaction currentTransaction;
    
    public UserDAO(){}

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
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public User findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
