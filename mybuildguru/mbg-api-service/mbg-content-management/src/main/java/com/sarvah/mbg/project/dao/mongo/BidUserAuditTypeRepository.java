package com.sarvah.mbg.project.dao.mongo;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.audit.UserAuditType;

public interface BidUserAuditTypeRepository extends
		CrudRepository<UserAuditType, Integer> {

	UserAuditType findByValue(String userAuditTypeVal);

}
