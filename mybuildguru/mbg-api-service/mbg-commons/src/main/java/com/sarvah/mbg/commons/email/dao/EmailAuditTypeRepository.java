/**
 * 
 */
package com.sarvah.mbg.commons.email.dao;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.mail.audit.EmailAuditType;

/**
 * @author Shivu
 *
 */
public interface EmailAuditTypeRepository extends
		CrudRepository<EmailAuditType, Integer> {

}
