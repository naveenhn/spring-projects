/**
 * 
 */
package com.sarvah.mbg.reviews.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu
 *
 */
public interface CommentUserDAO extends MongoRepository<User,String>{

}
