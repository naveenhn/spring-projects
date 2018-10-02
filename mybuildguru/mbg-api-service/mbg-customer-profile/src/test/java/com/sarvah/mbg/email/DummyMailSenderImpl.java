/**
 * 
 */
package com.sarvah.mbg.email;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sarvah.mbg.commons.email.MailSenderService;
import com.sarvah.mbg.domain.mail.MailContent;

/**
 * @author shivu s
 *
 */
public class DummyMailSenderImpl implements MailSenderService {
	
	private static final Logger logger = LoggerFactory.getLogger(DummyMailSenderImpl.class);
	
	@Autowired
	DummyAzureQueue azureQueue;
	
	@Override
	public void send(MailContent mailcontent) throws JMSException {
		
		
		azureQueue.getQueue().add(mailcontent);
		
		logger.info("From "+mailcontent.getFrom()+" "+"To "+mailcontent.getTo());
		
	}

	
}
