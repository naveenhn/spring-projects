///**
// * 
// */
//package com.sarvah.mbg.config.populator;
//
//import com.sarvah.mbg.dashboard.dao.EmailAuditTypeRepository;
//import com.sarvah.mbg.domain.mail.audit.EmailAuditType;
//
///**
// * @author shivu
// *
// */
//public class EmailAuditPopulatorBean {
//	EmailAuditTypeRepository emailAuditTypeRepository;
//
//	public EmailAuditPopulatorBean(
//			EmailAuditTypeRepository emailAuditTypeRepository) {
//		this.emailAuditTypeRepository = emailAuditTypeRepository;
//	}
//	
//	public void initEmailPopulatorBean(){
//		emailAuditTypeRepository.deleteAll();
//		EmailAuditType emailAuditType1=new EmailAuditType();
//		emailAuditType1.setEmailAuditTypeId(1);
//		emailAuditType1.setValue("SENT SUCCESS");
//		emailAuditType1.setDescription("Email Sent Successfully");
//		emailAuditTypeRepository.save(emailAuditType1);
//		
//		EmailAuditType emailAuditType2=new  EmailAuditType();
//		emailAuditType2.setEmailAuditTypeId(2);
//		emailAuditType2.setValue("SENT FAILURE");
//		emailAuditType2.setDescription("Email Sent Failure");
//		emailAuditTypeRepository.save(emailAuditType2);
//	}
//}
