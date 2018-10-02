package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;

/**
 * 
 * @author Raju
 *
 */
public interface DashboardUserDAO extends MongoRepository<User, String> {

	User findById(String uid);

	List<User> findByRoles_Name(String roleName);

	List<User> findByRoles_type(UserType valueOf);

	List<User> findByRoles_NameAndStatus(String string, UserStatus active);
}
