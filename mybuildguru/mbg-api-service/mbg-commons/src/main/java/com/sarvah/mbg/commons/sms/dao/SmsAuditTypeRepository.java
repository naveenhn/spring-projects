/**
 * 
 */
package com.sarvah.mbg.commons.sms.dao;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.sms.audit.SmsAuditType;

/**
 * @author Shivu
 *
 */
public interface SmsAuditTypeRepository extends
		CrudRepository<SmsAuditType, Integer> {

}
