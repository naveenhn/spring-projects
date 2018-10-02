//package com.sarvah.mbg.email;
//
//import static com.sarvah.mbg.commons.email.MailTemplateConstants.ADMIN_ABOUT_ARCH_ID;
//import static org.junit.Assert.assertTrue;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sarvah.mbg.MbgUserProfileTestConfiguration;
//import com.sarvah.mbg.commons.email.MailSenderService;
//import com.sarvah.mbg.domain.mail.MailContent;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MbgUserProfileTestConfiguration.class)
//public class TestSenderMailImpl {
//	
//	@Autowired
//	MailSenderService mailSenderService;
//	
//	@Autowired
//	DummyAzureQueue queue;
//	
//		
//	   @Test
//	   public void testAdd() throws Exception {
//		   
//		   Set<String> to = new HashSet<String>();
//		   to.add("abc@email.com");
//		  // Set<String> from=new HashSet<String>();
//		  // from.add("xyz");
//		   
//		   Set<String> bcc = new HashSet<String>();
//			bcc.add("shivananda.css@gmail.com");
//			
//		   MailContent content = new MailContent("harish990088@gmail.com",
//					to, "Hi", "shivu", "s", ADMIN_ABOUT_ARCH_ID, bcc);
//		   
//		  mailSenderService.send(content);
//		  
//		  assertTrue(queue.getQueue().contains(content) );
//		   
//		  }
//}
