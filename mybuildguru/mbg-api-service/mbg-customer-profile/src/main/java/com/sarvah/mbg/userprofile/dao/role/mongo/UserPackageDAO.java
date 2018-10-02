package com.sarvah.mbg.userprofile.dao.role.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;

public interface UserPackageDAO extends MongoRepository<UserPackage,String>{
	UserPackage findByName(String name);
}
