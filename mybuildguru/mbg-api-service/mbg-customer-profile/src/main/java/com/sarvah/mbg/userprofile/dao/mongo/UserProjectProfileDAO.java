/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserProjectProfile;

/**
 * @author Shiva
 *
 */
public interface UserProjectProfileDAO extends
		MongoRepository<UserProjectProfile, String> {
	List<UserProjectProfile> findByUser(User user);

	Long countByUser(String id);

	List<UserProjectProfile> findByUserAndProjectTypesHandled(User user,
			ProjectType valueOf);
}
