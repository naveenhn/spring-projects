/**
 * 
 */
package com.sarvah.mbg.commons.email.dao;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.mail.audit.EmailAudit;

/**
 * @author Shivu
 *
 */
public interface EmailAuditRepository extends
		CrudRepository<EmailAudit, Integer> {
	
	EmailAudit findByEmailMessageIdEquals(long messageId);
	

}
