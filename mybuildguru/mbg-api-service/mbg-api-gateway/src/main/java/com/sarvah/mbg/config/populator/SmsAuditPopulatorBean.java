/**
 * 
 */
package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.commons.sms.dao.SmsAuditTypeRepository;
import com.sarvah.mbg.domain.sms.audit.SmsAuditType;

/**
 * @author shivu
 *
 */
public class SmsAuditPopulatorBean {

	SmsAuditTypeRepository smsAuditRepository;

	public SmsAuditPopulatorBean(SmsAuditTypeRepository smsAuditRepository) {
		this.smsAuditRepository = smsAuditRepository;
	}
	
	public void initSmsPopulatorBean(){
		smsAuditRepository.deleteAll();
		
		SmsAuditType smsAuditType1=new SmsAuditType();
		smsAuditType1.setValue("SENT SUCCESS");
		smsAuditType1.setDescription("sms sent successfully");
		smsAuditRepository.save(smsAuditType1);
		
		SmsAuditType smsAuditType2=new SmsAuditType();
		smsAuditType2.setValue("SENT FAILURE");
		smsAuditType2.setDescription("mail sent failure");
		smsAuditRepository.save(smsAuditType2);
	}

}
