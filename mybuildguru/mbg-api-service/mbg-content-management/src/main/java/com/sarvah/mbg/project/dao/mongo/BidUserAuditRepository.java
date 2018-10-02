/**
 * 
 */
package com.sarvah.mbg.project.dao.mongo;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.audit.UserAudit;

/**
 * @author Shivu
 *
 */
public interface BidUserAuditRepository extends
		CrudRepository<UserAudit, Integer> {

}
