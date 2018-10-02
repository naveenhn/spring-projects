package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.ordermgmt.OrderStatement;

public interface OrderStatementRepository extends
		MongoRepository<OrderStatement, String> {

	List<OrderStatement> findByUser(User user);

	OrderStatement findByStatementId(String statementId);

}
