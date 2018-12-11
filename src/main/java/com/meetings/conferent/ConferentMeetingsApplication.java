package com.meetings.conferent;

import java.util.Date;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.meetings.conferent.config.AppConfig;
import com.meetings.conferent.dao.SessionFactoryClass;
import com.meetings.conferent.model.Meeting;
import com.meetings.conferent.model.Room;
import com.meetings.conferent.model.Site;
import com.meetings.conferent.model.User;
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
		//saveUser("Joro");
	}
	
	private static void saveUser(String name) throws Exception {
		Session session = SessionFactoryClass.getSessionFactoryInstance().openSession();
		Transaction txn = session.beginTransaction();
		User user = new User();
		Site site = new Site();
		site.setName("Site");
		site.setLocation("Tam nqkyde");
		session.save(site);
		Room room = new Room();
		room.setName("Staq");
		room.setSite(site);
		room.setLocation("i tq e tam");
		session.save(room);
		user.setAdmin(true);
		user.setCompany("kompaniqta");
		user.setFirstName(name);
		user.setLastName("Marsi");
		user.setPasscode(EncriptionUtil.encrypt("123444456"));
		session.save(user);
		txn.commit();
	}
}
