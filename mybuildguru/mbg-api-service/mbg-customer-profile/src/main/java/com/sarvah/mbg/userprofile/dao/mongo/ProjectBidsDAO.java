/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
public interface ProjectBidsDAO extends MongoRepository<Bid, String> {

	List<Bid> findByProject(Project project);

	List<Bid> findByUser(User bidUser);

}
