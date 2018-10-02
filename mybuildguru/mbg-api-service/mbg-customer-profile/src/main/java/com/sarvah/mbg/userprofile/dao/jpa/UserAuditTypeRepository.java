/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.audit.UserAuditType;

/**
 * @author Shivu
 *
 */
public interface UserAuditTypeRepository extends
		CrudRepository<UserAuditType, Integer> {

	UserAuditType findByValue(String userAuditTypeVal);

}
