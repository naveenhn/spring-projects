/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.audit.UserAudit;

/**
 * @author Shivu
 *
 */
public interface UserAuditRepository extends CrudRepository<UserAudit, Integer> {

}
