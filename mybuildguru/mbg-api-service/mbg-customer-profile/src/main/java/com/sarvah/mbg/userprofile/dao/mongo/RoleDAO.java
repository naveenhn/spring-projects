package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Role;

public interface RoleDAO extends MongoRepository<Role, String> {
	Role findByName(String name);

	Role findByNameAllIgnoreCase(String name);

	Role findById(String id);

	Set<Role> findByName();

}
