package com.meetings.conferent;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meetings.conferent.config.AppConfig;
import com.meetings.conferent.dao.SessionFactoryClass;
import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;
import com.meetings.conferent.model.User;
import com.meetings.conferent.model.UserRole;
import com.meetings.conferent.service.MeetingService;
import com.meetings.conferent.utils.EncriptionUtil;

@SpringBootApplication(scanBasePackages = {"com.meetings.conferent"})
public class ConferentMeetingsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConferentMeetingsApplication.class, args);
		/*AnnotationConfigApplicationContext context = 
	            new AnnotationConfigApplicationContext(AppConfig.class);
		MeetingService meetingService = context.getBean(MeetingService.class);
		Meeting newMeeting = new Meeting();
		newMeeting.setRoom(new Room(1, "Staq", new Site(1, "Site", "loc"), "romLoc", false));
		newMeeting.setTitle("mn qka sreshta");
		meetingService.insert(newMeeting);
		for(Meeting meeting: meetingService.getMeetingsForRoom(1)) {
			System.out.println(meeting.getTitle());
		}
		context.close();*/
		//saveStuff("sdasf");
		//saveUser("Joro");
	}
	
	private static void saveStuff(String name) throws Exception {
		Session session = SessionFactoryClass.getSessionFactoryInstance().openSession();
		Transaction txn = session.beginTransaction();
		User user = new User();
		User user2 = new User();
		User user3 = new User();
		/*Site site = new Site();
		site.setName("Site3");
		site.setLocation("Tam nqkyde3");
		session.save(site);
		Room room = new Room();
		room.setName("Staq");
		room.setSite(site);
		room.setLocation("i tq e tam");
		session.save(room);*/
		user.setUsername("joro");
		user.setFirstName("Georgi");
		user.setLastName("Georgev");
		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode("pass"));
		session.save(user);
		user2.setUsername("joro2");
		user2.setFirstName("Georgi");
		user2.setLastName("Georgev");
		user2.setEnabled(true);
		user2.setPassword(new BCryptPasswordEncoder().encode("pass"));
		session.save(user2);
		user3.setUsername("joro3");
		user3.setFirstName("Georgi");
		user3.setLastName("Georgev");
		user3.setEnabled(true);
		user3.setPassword(new BCryptPasswordEncoder().encode("pass"));
		session.save(user3);
		txn.commit();
	}
	
	private static void saveUser(String name) throws Exception{
		Session session = SessionFactoryClass.getSessionFactoryInstance().openSession();
		Transaction txn = session.beginTransaction();
		User user = new User();
		user.setUsername("admin");
		user.setFirstName("Admin");
		user.setLastName("Adminev");
		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		session.save(user);
		Set<UserRole> userRoles = new HashSet<UserRole>();
		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole("ADMIN");
		userRoles.add(role);
		session.save(role);
		user.setUserRole(userRoles);
		session.update(user);
		txn.commit();
	}
}
