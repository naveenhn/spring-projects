/**
 * 
 */
package com.sarvah.mbg.project.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
public interface BidsDAO extends MongoRepository<Bid, String> {

	List<Bid> findByProject(Project project);

	long countByUser(User user);

	List<Bid> findByUser(User user);

	long countByProject(Project project);

	long countByUser_Id(String uid);

	long countByProjectAndUser_Roles_Name(Project project, String string);

	List<Bid> findByProjectAndUser_Roles_Name(Project project, String upperCase);

}
