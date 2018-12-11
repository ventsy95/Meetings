package com.meetings.conferent.dao;

import com.meetings.conferent.model.User;

public interface IUserDAO {
	void addUser(User user);
	void updateUser(User user);
	void deleteUser(User user);
	User findById(long id);
}