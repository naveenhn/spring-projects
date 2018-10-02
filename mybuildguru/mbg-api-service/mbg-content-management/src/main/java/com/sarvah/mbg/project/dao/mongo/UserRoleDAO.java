package com.sarvah.mbg.project.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Role;

/**
 * 
 * @author Raju
 *
 */

public interface UserRoleDAO extends MongoRepository<Role, String> {
	Role findByName(String name);


}
