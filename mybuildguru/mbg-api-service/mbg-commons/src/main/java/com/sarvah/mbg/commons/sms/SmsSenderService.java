package com.sarvah.mbg.commons.sms;

import javax.jms.JMSException;

import com.sarvah.mbg.domain.sms.SmsContent;

public interface SmsSenderService {
	/**
	 * send method to
	 * @param smscontent
	 * @throws JMSException 
	 */
	void send(SmsContent smscontent) throws JMSException;

}
