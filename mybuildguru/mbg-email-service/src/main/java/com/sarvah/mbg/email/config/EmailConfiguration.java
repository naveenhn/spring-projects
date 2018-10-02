/**
 * 
 */
package com.sarvah.mbg.email.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.sarvah.mbg.commons.email.MailAuditService;
import com.sarvah.mbg.commons.email.MailAuditServiceImpl;
import com.sarvah.mbg.email.receiver.EmailQueueSubscriber;
import com.sarvah.mbg.email.receiver.Mailer;

/**
 * @author naveen
 *
 */
@Configuration
public class EmailConfiguration {

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Bean
	public Mailer mailer() {
		Mailer mailer = new Mailer(javaMailSender, velocityEngine);
		return mailer;
	}

	@Bean
	public EmailQueueSubscriber emailRecieverService() {

		try {
			return new EmailQueueSubscriber();
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to create a emaulQueur subscriber");
		}

	}

	@Bean
	public MailAuditService mailAuditService() {
		return new MailAuditServiceImpl();
	}

}
