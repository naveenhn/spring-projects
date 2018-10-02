package com.sarvah.mbg.promotion.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Role;

public interface DashBoardRoleDAO extends MongoRepository<Role, String> {

	Role findByName(String upperCase);
}
