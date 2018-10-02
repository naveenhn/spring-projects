/**
 * 
 */
package com.sarvah.mbg.commons.sms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.sms.dao.SmsAuditRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditTypeRepository;
import com.sarvah.mbg.domain.sms.SmsContent;
import com.sarvah.mbg.domain.sms.audit.SmsAudit;
import com.sarvah.mbg.domain.sms.audit.SmsAuditType;

/**
 * @author Shivu
 *
 */
@Service
public class SmsAuditServiceImpl implements SmsAuditService {

	@Autowired
	private SmsAuditTypeRepository smsAuditTypeRepository;

	@Autowired
	private SmsAuditRepository smsAuditRepository;

	public void log(SmsContent smscontent, long messageId) {
		String to = null;
		SmsAudit smsAudit = new SmsAudit();
		for (String toSms : smscontent.getTo()) {
			to = toSms;
		}
		SmsAuditType smsAuditType = smsAuditTypeRepository.findOne(1);
		smsAudit.setSmsAuditType(smsAuditType);
		smsAudit.setSmsTo(to);
		smsAudit.setSmsMessageId(messageId);
		smsAudit.setSmsCreatedBy("Buildonn");
		smsAudit.setSmsModifiedBy("Buildonn");
		smsAudit.setSmsCreatedTime(new Date());
		smsAudit.setSmsLastModifiedTime(new Date());
		smsAuditRepository.save(smsAudit);

	}

	public boolean isMessageIdPresent(long messageId) {
		SmsAudit smsAudit = smsAuditRepository
				.findBySmsMessageIdEquals(messageId);
		if (smsAudit != null) {
			return true;

		}

		return false;
	}

}
