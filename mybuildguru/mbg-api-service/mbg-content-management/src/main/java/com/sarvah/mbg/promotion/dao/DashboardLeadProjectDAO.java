/**
 * 
 */
package com.sarvah.mbg.promotion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.LeadProject;

/**
 * @author Shivu
 *
 */
public interface DashboardLeadProjectDAO extends
		MongoRepository<LeadProject, String> {

	List<LeadProject> findBySalesExecutive_Id(String id);

	List<LeadProject> findByLastModifiedDateBetweenAndSalesExecutive_Id(
			Date sdate, Date edate, String id);

	LeadProject findByIdAndCreatedDateBetween(String id, Date sdate1,
			Date sdate2);

}
