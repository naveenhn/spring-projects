/**
 * 
 */
package com.sarvah.mbg.sms.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.commons.sms.SmsAuditService;
import com.sarvah.mbg.commons.sms.SmsAuditServiceImpl;
import com.sarvah.mbg.sms.receiver.SmsQueueSubscriber;
import com.sarvah.mbg.sms.receiver.SmsSender;

/**
 * @author naveen
 *
 */
@Configuration
public class SmsServiceConfiguration {

	@Bean
	public HttpClient httpClient() {
		return HttpClients.createDefault();
	}

	@Bean
	public SmsSender smsSender() {
		return new SmsSender();
	}

	@Bean
	public SmsQueueSubscriber smsQueueSubscriber() {

		try {
			return new SmsQueueSubscriber();
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to create a sms queue subscriber");
		}
	}

	@Bean
	public SmsAuditService smsAuditService() {
		return new SmsAuditServiceImpl();
	}
}
