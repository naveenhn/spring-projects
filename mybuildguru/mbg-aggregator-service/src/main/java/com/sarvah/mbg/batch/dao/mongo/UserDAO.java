/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author USER
 *
 */
public interface UserDAO extends MongoRepository<User, String>{

}
