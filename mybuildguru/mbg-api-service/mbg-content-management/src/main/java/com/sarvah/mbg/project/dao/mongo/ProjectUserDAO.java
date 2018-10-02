/**
 * 
 */
package com.sarvah.mbg.project.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
public interface ProjectUserDAO extends MongoRepository<User, String> {

	User findByUsername(String userName);

	User findById(String uid);
}
