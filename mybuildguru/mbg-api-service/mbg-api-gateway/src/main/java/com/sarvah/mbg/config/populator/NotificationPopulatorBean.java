package com.sarvah.mbg.config.populator;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationType;
import com.sarvah.mbg.notification.dao.mongo.NotificationDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

public class NotificationPopulatorBean {
	
	
	NotificationDAO notificationDAO;
	UserDAO userDAO;
	
	public NotificationPopulatorBean(NotificationDAO notificationDAO,UserDAO userDAO){
		this.notificationDAO=notificationDAO;
		this.userDAO=userDAO;
	}
	
	public void initNotification(){
		 notificationDAO.deleteAll();
//		 List<User> users = userDAO.findByUsername("admin@jaquar.com",
//					new PageRequest(0, 10));
//		 
//		 Notification notification=new Notification();
//		 notification.setType(NotificationType.ALERTS);
//		 notification.setUser(users.get(0));
//		 notification.setText("Alert Notification text");
//		 
//		 Description description=new Description();
//		 description.setLang("eng");
//		 description.setVal("Alert notification description");
//		 
//		 notification.setDesc(description);
//		 
//		 notificationDAO.insert(notification);
//		 
//		 //
//		 
//		 Notification notification1=new Notification();
//		 notification1.setType(NotificationType.ACTION_ITEM);
//		 notification1.setUser(users.get(0));
//		 notification1.setText("Action item Notification text");
//		 
//		 Description description1=new Description();
//		 description1.setLang("eng");
//		 description1.setVal("Action item notification description");
//		 
//		 notification1.setDesc(description1);
//		 
//		 notificationDAO.insert(notification1);
//		 
		 
	}

}
 