package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public interface UserProjectDAO extends MongoRepository<Project, String> {

	Project findByName(String name);

	List<Project> findByNameAndUser(String name, User user);

	long countByNameAndUser(String name, User user);

	long countByTypeAndUser(ProjectType type, User user);

	long countByAddress_CityAndUser(String city, User user);

	long countByUser(User user);

	long countByAddress_AddressLine1LikeAndUser(String addrLine1, User user);

	long countByAddress_AddressLine2LikeAndUser(String addrLine2, User user);

	List<Project> findByUser(User user);

	List<Project> findByTypeAndUser(String type, User user);

	List<Project> findByAddress_AddressLine1AndUser(String addrLine1, User user);

	List<Project> findByAddress_AddressLine2AndUser(String addrLine2, User user);

	List<Project> findByAddress_CityAndUser(String city, User user);

	List<Project> findByAddress_ZipcodeAndUser(Integer zipcode, User user);

	List<Project> findByAddress_Location(Point point);

	long countByUserId(String uid);

	Project findByIdAndUser(String uid, User user);
}