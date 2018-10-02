package com.sarvah.mbg.commons.email;

import javax.jms.JMSException;

import com.sarvah.mbg.domain.mail.MailContent;



public interface MailSenderService {
	/**
	 * Send method to send Information to Queue
	 * 
	 * @param content
	 * @throws JMSException
	 */
	void send(MailContent content) throws JMSException;
}
