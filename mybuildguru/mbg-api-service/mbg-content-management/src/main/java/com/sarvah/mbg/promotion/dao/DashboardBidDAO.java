package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
/**
 * 
 * @author Raju
 *
 */

public interface DashboardBidDAO extends MongoRepository<Bid, String> {
	
	List<Bid> findByProject(Project project);
  
}
