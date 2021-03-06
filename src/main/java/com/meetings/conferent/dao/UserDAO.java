package com.meetings.conferent.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.meetings.conferent.model.User;
import com.meetings.conferent.model.UserRole;

import static com.meetings.conferent.dao.SessionFactoryClass.getSessionFactoryInstance;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO implements IUserDAO {
	private Session currentSession;
	private Transaction currentTransaction;

	public UserDAO() {
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
	public void addUser(User user) {
		getCurrentSession().save(user);
	}

	// @Override
	public void addUserRole(UserRole role) {
		getCurrentSession().save(role);
	}

	@Override
	public void updateUser(User user) {
		getCurrentSession().update(user);
	}

	@Override
	public void deleteUser(User user) {
		if (getCurrentSession() != null && getCurrentSession().isOpen()) {
			getCurrentSession().delete(user);
		} else {
			openCurrentSession().delete(user);
		}
	}

	@Override
	public User findById(long id) {
		User user = (User) getCurrentSession().get(User.class, id);
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		if (getCurrentSession() != null && getCurrentSession().isOpen()) {
			users = getCurrentSession().createQuery("from User").list();
		} else {
			users = openCurrentSession().createQuery("from User").list();
		}
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = getCurrentSession().createQuery("from User where username= :username")
				.setParameter("username", username).list();

		if (users.size() > 0) {
			User user = users.get(0);
			return user;
		} else {
			return null;
		}

	}
}
