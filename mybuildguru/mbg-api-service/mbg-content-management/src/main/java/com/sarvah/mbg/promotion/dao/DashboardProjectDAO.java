package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;

/**
 * 
 * @author Raju
 *
 */

public interface DashboardProjectDAO extends MongoRepository<Project, String> {
	List<Project> findByUser_Id(String uid);

	// User gives his location and we return all projects within certain radius
	List<Project> findByAddressLocationNear(Point point, Distance distance);

	// User gives his location and we return projects(of certain type) within
	// certain radius
	List<Project> findByTypeAndAddressLocationNear(ProjectType projectType,
			Point point, Distance distance);

	List<Project> findByType(ProjectType projectType);

	List<Project> findByTypeAndStatus(ProjectType projectType, String status);

	List<Project> findByTypeAndStatusAndAddressLocationNear(ProjectType type1,
			String string, Point point, Distance distance);
}
