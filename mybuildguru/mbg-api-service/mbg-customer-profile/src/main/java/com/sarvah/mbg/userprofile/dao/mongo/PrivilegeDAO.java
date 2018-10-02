package com.sarvah.mbg.userprofile.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;

/**
 * @author Shiva
 *
 */
public interface PrivilegeDAO extends MongoRepository<Privilege, String> {

	/**
	 * @param name
	 * @return
	 */
	Privilege findByName(String name);

}
