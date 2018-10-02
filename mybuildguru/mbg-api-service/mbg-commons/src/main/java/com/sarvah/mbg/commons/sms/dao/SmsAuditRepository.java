/**
 * 
 */
package com.sarvah.mbg.commons.sms.dao;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.sms.audit.SmsAudit;

/**
 * @author Shivu
 *
 */
public interface SmsAuditRepository extends CrudRepository<SmsAudit, Integer> {

	SmsAudit findBySmsMessageIdEquals(long messageId);

}
