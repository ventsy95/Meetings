package com.meetings.conferent.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;
import com.meetings.conferent.model.User;

public class SessionFactoryClass {

	private static SessionFactory instance;

	public static SessionFactory getSessionFactoryInstance() {
		if (instance == null) {
			instance = getSessionFactory();
		}
		return instance;
	}

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class).addAnnotatedClass(Meeting.class).addAnnotatedClass(Room.class)
				.addAnnotatedClass(Site.class).configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	private static SessionFactory getSessionFactory() {
		return configureSessionFactory();
	}
}
