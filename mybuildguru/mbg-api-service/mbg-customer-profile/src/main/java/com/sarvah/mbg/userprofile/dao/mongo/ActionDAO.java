package com.sarvah.mbg.userprofile.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Action;

public interface ActionDAO extends MongoRepository<Action,String>{

	Action findByName(String string);
}
