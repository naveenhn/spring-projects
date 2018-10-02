package com.sarvah.mbg.batch.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;

public interface RoleDAO extends MongoRepository<Role, String> {

	Role findByType(UserType dealer);

}
