/**
 * 
 */
package com.sarvah.mbg.project.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;

/**
 * @author Shiva
 *
 */
public interface ProjectPrivilegeDAO extends MongoRepository<Privilege, String> {

	Privilege findByName(String name);

}
