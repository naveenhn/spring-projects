/**
 * 
 */
package com.sarvah.mbg.commons.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.email.dao.EmailAuditRepository;
import com.sarvah.mbg.commons.email.dao.EmailAuditTypeRepository;
import com.sarvah.mbg.domain.mail.MailContent;
import com.sarvah.mbg.domain.mail.audit.EmailAudit;
import com.sarvah.mbg.domain.mail.audit.EmailAuditType;

/**
 * @author Shivu
 *
 */
@Service
public class MailAuditServiceImpl implements MailAuditService {

	@Autowired
	private EmailAuditTypeRepository emailAuditTypeRepository;

	@Autowired
	private EmailAuditRepository emailAuditRepository;

	public void log(MailContent mailcontent, long messageId) {

		String to = null;
		EmailAudit emailAudit = new EmailAudit();
		for (String toEmail : mailcontent.getTo()) {
			to = toEmail;
		}
		EmailAuditType emailAuditType = emailAuditTypeRepository.findOne(1);
		emailAudit.setAuditType(emailAuditType);
		emailAudit.setEmailFrom(mailcontent.getFrom());
		emailAudit.setEmailTo(to);
		emailAudit.setEmailSubject(mailcontent.getSubjectInfo());
		emailAudit.setCreatedTime(new Date());
		emailAudit.setLastmodifiedTime(new Date());
		emailAudit.setCreatedBy("Buildonn");
		emailAudit.setModifiedBy("Buildonn");
		emailAudit.setEmailCategory("abcd");
		emailAudit.setEmailMessageId(messageId);		
		emailAuditRepository.save(emailAudit);
	}

	@Override
	public boolean isMessageIdPresent(long messageId) {
		
		EmailAudit emailAudit = emailAuditRepository.findByEmailMessageIdEquals(messageId);
		if(emailAudit != null ){
			return true;
			
		}
		
		return false;
	}

}
