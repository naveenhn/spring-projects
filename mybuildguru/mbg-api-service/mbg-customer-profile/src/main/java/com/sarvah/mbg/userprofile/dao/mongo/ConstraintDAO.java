package com.sarvah.mbg.userprofile.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.role.Constraint;

public interface ConstraintDAO extends MongoRepository<Constraint,String> {

}
