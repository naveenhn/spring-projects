/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sarvah.mbg.domain.mongo.aceproject.LeadProject;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;

/**
 * @author Shivu
 *
 */
public interface LeadProjectDAO extends MongoRepository<LeadProject, String> {

	List<LeadProject> findByNameLikeAllIgnoreCase(String projName);

	List<LeadProject> findByType(ProjectType valueOf);

	List<LeadProject> findByAddress_AddressLine1LikeAllIgnoreCase(
			String addrLine1);

	List<LeadProject> findByAddress_AddressLine2LikeAllIgnoreCase(
			String addrLine2);

	List<LeadProject> findByAddress_CityAllIgnoreCase(String city);

	List<LeadProject> findByAddress_Zipcode(int parseInt);

	List<LeadProject> findByAddress_Location(Point point);

	List<LeadProject> findByOwner_Id(String id);

	List<LeadProject> findByProjectStageLikeAllIgnoreCase(String projectStage);

	List<LeadProject> findByFollowupDate(String followupDate);

	List<LeadProject> findByContactedDate(String contactedDate);

	List<LeadProject> findByAreaLikeAllIgnoreCase(String area);

	List<LeadProject> findBySalesExecutive_FirstNameLikeOrSalesExecutive_LastNameLikeOrSalesExecutive_FullNameLikeAllIgnoreCase(
			String teleName, String teleName2, String teleName3);

	List<LeadProject> findByOwner_FirstNameLikeOrOwner_LastNameLikeOrOwner_FullNameLikeAllIgnoreCase(
			String costomerName, String costomerName2, String costomerName3);

	List<LeadProject> findBySalesExecutive_Id(String id);

	Page<LeadProject> findByNameLikeAllIgnoreCase(String projName,
			Pageable pageable);

	Page<LeadProject> findByType(ProjectType valueOf, Pageable pageable);

	Page<LeadProject> findByAddress_AddressLine1LikeAllIgnoreCase(
			String addrLine1, Pageable pageablet);

	Page<LeadProject> findByAddress_AddressLine2LikeAllIgnoreCase(
			String addrLine2, Pageable pageable);

	Page<LeadProject> findByAddress_CityAllIgnoreCase(String city,
			Pageable pageable);

	Page<LeadProject> findByAddress_Zipcode(int parseInt, Pageable pageable);

	Page<LeadProject> findByAddress_Location(Point point, Pageable pageable);

	Page<LeadProject> findByOwner_Id(String id, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_Id(String id, Pageable pageable);

	Page<LeadProject> findByAreaLikeAllIgnoreCase(String area, Pageable pageable);

	Page<LeadProject> findByProjectStageLikeAllIgnoreCase(String projectStage,
			Pageable pageable);

	Page<LeadProject> findByFollowupDate(String followupDate, Pageable pageable);

	Page<LeadProject> findByContactedDate(String contactedDate,
			Pageable pageable);

	Page<LeadProject> findByNameLikeOrTypeOrProjectStageOrAreaOrFollowupDateOrContactedDateAllIgnoreCase(
			String searchVal, String searchVal2, String searchVal3,
			String searchVal4, String searchVal5, String searchVal6,
			Pageable pageable);

	Page<LeadProject> findByNameLikeOrZipcodeOrAreaLikeOrFollowupDateAllIgnoreCase(
			String searchVal, String searchVal2, String searchVal3,
			String searchVal4, Pageable pageable);

	Page<LeadProject> findByOwner_IdAndTypeAndProjectStage(String id,
			ProjectType valueOf, String projectStage, Pageable pageable);

	@Query("{$and:[{'type' : ?0},{'projectStage' : ?1},{$or:[{'name' : {$regex : ?2, $options: 'i'}},{'area' : {$regex : ?3, $options: 'i'}},{'followupDate' : {$regex : ?4, $options: 'i'}},{'zipcode' : {$regex : ?5, $options: 'i'}},{'referencedBy' : {$regex : ?6, $options: 'i'}},{'called' : {$regex : ?7, $options: 'i'}}]}]}")
	Page<LeadProject> findByTypeAndProjectStageLikeOrNameLikeOrAreaLikeOrFollowupDateOrAddress_ZipcodeOrReferencedByLikeOrRequirementNoteContainsAllIgnoreCase(
			ProjectType type, String projectStage, String name, String area,
			String followupDate, String zipcode, String referencedBy,
			String materialsRequired, Pageable pageable);

	Page<LeadProject> findByOwner_IdAndType(String id, ProjectType valueOf,
			Pageable pageable);

	@Query("{$and:[{'type' : ?0},{$or:[{'name' : {$regex : ?1, $options: 'i'}},{'area' : {$regex : ?2, $options: 'i'}},{'followupDate' : {$regex : ?3, $options: 'i'}},{'zipcode' : {$regex : ?4, $options: 'i'}}]}]}")
	Page<LeadProject> findByTypeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
			ProjectType valueOf, String searchVal, String searchVal2,
			String searchVal4, String zipcode, Pageable pageable);

	Page<LeadProject> findByTypeAndProjectStageAllIgnoreCase(
			ProjectType valueOf, String projectStage, Pageable pageable);

	Page<LeadProject> findByOwner_IdAndProjectStageAllIgnoreCase(String id,
			String projectStage, Pageable pageable);

	@Query("{$and:[{'projectStage' : ?0},{$or:[{'name' : {$regex : ?1, $options: 'i'}},{'area' : {$regex : ?2, $options: 'i'}},{'followupDate' : {$regex : ?3, $options: 'i'}},{'zipcode' : {$regex : ?4, $options: 'i'}}]}]}")
	Page<LeadProject> findByProjectStageLikeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
			String projectStage, String searchVal2, String searchVal3,
			String searchVal4, String zipcode, Pageable pageable);

	Page<LeadProject> findByNameLikeOrAreaLikeOrFollowupDateAllIgnoreCase(
			String searchVal, String searchVal2, String searchVal3,
			Pageable pageable);

	List<LeadProject> findByReferencedByLikeAllIgnoreCase(String referencedBy);

	List<LeadProject> findByCalledIsTrue(Boolean valueOf);

	List<LeadProject> findByReasonForRejectLikeAllIgnoreCase(
			String reasonForReject);

	List<LeadProject> findByRequirementNoteContains(String requirement);

	List<LeadProject> findByCalledIsFalse(Boolean valueOf);

	Page<LeadProject> findByReferencedByLikeAllIgnoreCase(String referencedBy,
			Pageable pageable);

	Page<LeadProject> findByCalledIsTrue(Boolean valueOf, Pageable pageable);

	Page<LeadProject> findByCalledIsFalse(Boolean valueOf, Pageable pageable);

	Page<LeadProject> findByReasonForRejectLikeAllIgnoreCase(
			String reasonForReject, Pageable pageable);

	Page<LeadProject> findByRequirementNoteContains(String requirement,
			Pageable pageables);

	Page<LeadProject> findBySalesExecutive_IdAndOwner_IdAndTypeAndProjectStage(
			String fieldAssociateId, String id, ProjectType valueOf,
			String projectStage, Pageable pageable);

	@Query("{$and:[{'salesExecutive':?0},{'type' : ?1},{'projectStage' : ?2},{$or:[{'name' : {$regex : ?3, $options: 'i'}},{'area' : {$regex : ?4, $options: 'i'}},{'followupDate' : {$regex : ?5, $options: 'i'}},{'zipcode' : {$regex : ?6, $options: 'i'}},{'referencedBy' : {$regex : ?7, $options: 'i'}},{'called' : {$regex : ?8, $options: 'i'}}]}]}")
	Page<LeadProject> findBySalesExecutive_IdAndTypeAndProjectStageLikeOrNameLikeOrAreaLikeOrFollowupDateOrAddress_ZipcodeOrReferencedByLikeOrRequirementNoteContainsAllIgnoreCase(
			String fieldAssociateId, ProjectType type, String projectStage,
			String name, String area, String followupDate, String zipcode,
			String referencedBy, String materialsRequired, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndOwner_IdAndType(
			String fieldAssociateId, String id, ProjectType valueOf,
			Pageable pageable);

	@Query("{$and:[{'salesExecutive':?0},{'type' : ?1},{$or:[{'name' : {$regex : ?2, $options: 'i'}},{'area' : {$regex : ?3, $options: 'i'}},{'followupDate' : {$regex : ?4, $options: 'i'}},{'zipcode' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<LeadProject> findBySalesExecutive_IdAndTypeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
			String salesExecutiveId, ProjectType valueOf, String searchVal,
			String searchVal2, String searchVal4, String zipcode,
			Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndTypeAndProjectStageAllIgnoreCase(
			String fieldAssociateId, ProjectType valueOf, String projectStage,
			Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndOwner_IdAndProjectStageAllIgnoreCase(
			String fieldAssociateId, String id, String projectStage,
			Pageable pageable);

	@Query("{$and:[{'salesExecutive':?0},{'projectStage' : ?1},{$or:[{'name' : {$regex : ?2, $options: 'i'}},{'area' : {$regex : ?3, $options: 'i'}},{'followupDate' : {$regex : ?4, $options: 'i'}},{'zipcode' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<LeadProject> findBySalesExecutive_IdAndProjectStageLikeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
			String salesExecutiveId, String projectStage, String searchVal2,
			String searchVal3, String searchVal4, String zipcode,
			Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndOwner_Id(
			String fieldAssociateId, String id, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndNameLikeOrAreaLikeOrFollowupDateAllIgnoreCase(
			String fieldAssociateId, String searchVal, String searchVal2,
			String searchVal3, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndNameLikeOrZipcodeOrAreaLikeOrFollowupDateAllIgnoreCase(
			String fieldAssociateId, String searchVal, String searchVal2,
			String searchVal3, String searchVal4, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndNameLikeAllIgnoreCase(
			String fieldAssociateId, String projName, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndType(String fieldAssociateId,
			ProjectType valueOf, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAddress_AddressLine1LikeAllIgnoreCase(
			String fieldAssociateId, String addrLine1, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAddress_AddressLine2LikeAllIgnoreCase(
			String fieldAssociateId, String addrLine2, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAddress_CityAllIgnoreCase(
			String fieldAssociateId, String city, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAddress_Zipcode(
			String fieldAssociateId, int parseInt, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAddress_Location(
			String fieldAssociateId, Point point, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndAreaLikeAllIgnoreCase(
			String fieldAssociateId, String area, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndProjectStageLikeAllIgnoreCase(
			String fieldAssociateId, String projectStage, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndFollowupDate(
			String fieldAssociateId, String followupDate, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndContactedDate(
			String fieldAssociateId, String contactedDate, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndReferencedByLikeAllIgnoreCase(
			String fieldAssociateId, String referencedBy, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndCalledIsTrue(
			String fieldAssociateId, Boolean valueOf, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndCalledIsFalse(
			String fieldAssociateId, Boolean valueOf, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndReasonForRejectLikeAllIgnoreCase(
			String fieldAssociateId, String reasonForReject, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndRequirementNoteContains(
			String fieldAssociateId, String requirement, Pageable pageable);

	LeadProject findBySalesExecutive_IdAndId(String fieldAssociateId,
			String leadProjectId);

	Page<LeadProject> findByStatus(String status, Pageable pageable);

	List<LeadProject> findByStatus(String status);

	Page<LeadProject> findBySalesExecutive_IdAndStatus(String fieldAssociateId,
			String status, Pageable pageable);

	List<LeadProject> findBySalesExecutive_IdAndStatus(String fieldAssociateId,
			String status);

	List<LeadProject> findBySalesExecutive_IdAndNameLikeAllIgnoreCase(
			String fieldAssociateId, String projName);

	List<LeadProject> findBySalesExecutive_IdAndType(String fieldAssociateId,
			ProjectType valueOf);

	List<LeadProject> findBySalesExecutive_IdAndAddress_AddressLine1LikeAllIgnoreCase(
			String fieldAssociateId, String addrLine1);

	List<LeadProject> findBySalesExecutive_IdAndAddress_AddressLine2LikeAllIgnoreCase(
			String fieldAssociateId, String addrLine2);

	List<LeadProject> findBySalesExecutive_IdAndAddress_CityAllIgnoreCase(
			String fieldAssociateId, String city);

	List<LeadProject> findBySalesExecutive_IdAndAddress_Zipcode(
			String fieldAssociateId, int parseInt);

	List<LeadProject> findBySalesExecutive_IdAndAddress_Location(
			String fieldAssociateId, Point point);

	Collection<? extends LeadProject> findBySalesExecutive_IdAndOwner_Id(
			String fieldAssociateId, String id);

	List<LeadProject> findBySalesExecutive_IdAndAreaLikeAllIgnoreCase(
			String fieldAssociateId, String area);

	List<LeadProject> findBySalesExecutive_IdAndProjectStageLikeAllIgnoreCase(
			String fieldAssociateId, String projectStage);

	List<LeadProject> findBySalesExecutive_IdAndFollowupDate(
			String fieldAssociateId, String followupDate);

	List<LeadProject> findBySalesExecutive_IdAndContactedDate(
			String fieldAssociateId, String contactedDate);

	List<LeadProject> findBySalesExecutive_IdAndReferencedByLikeAllIgnoreCase(
			String fieldAssociateId, String referencedBy);

	List<LeadProject> findBySalesExecutive_IdAndCalledIsTrue(
			String fieldAssociateId, Boolean valueOf);

	List<LeadProject> findBySalesExecutive_IdAndCalledIsFalse(
			String fieldAssociateId, Boolean valueOf);

	List<LeadProject> findBySalesExecutive_IdAndReasonForRejectLikeAllIgnoreCase(
			String fieldAssociateId, String reasonForReject);

	List<LeadProject> findBySalesExecutive_IdAndRequirementNoteContains(
			String fieldAssociateId, String requirement);

	Page<LeadProject> findByFollowupDateNotNull(Pageable pageable);

	Page<LeadProject> findByFollowupDateNull(Pageable pageable);

	List<LeadProject> findBySalesExecutive_IdAndFollowupDateNotNull(
			String fieldAssociateId);

	List<LeadProject> findBySalesExecutive_IdAndFollowupDateNull(
			String fieldAssociateId);

	List<LeadProject> findByFollowupDateNotNull();

	List<LeadProject> findByFollowupDateNull();

	Page<LeadProject> findBySalesExecutive_IdAndFollowupDateNotNull(
			String fieldAssociateId, Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndFollowupDateNull(
			String fieldAssociateId, Pageable pageable);

	List<LeadProject> findByFollowupDateIn(Set<String> followupDateSet);

	Page<LeadProject> findByFollowupDateIn(Set<String> followupDateSet,
			Pageable pageable);

	Page<LeadProject> findBySalesExecutive_IdAndFollowupDate(
			String fieldAssociateId, Set<String> followupDateSet,
			Pageable pageable);

	List<LeadProject> findBySalesExecutive_IdAndFollowupDateIn(
			String fieldAssociateId, Set<String> followupDateSet);

	Page<LeadProject> findBySalesExecutive_IdAndFollowupDateIn(
			String fieldAssociateId, Set<String> followupDateSet,
			Pageable pageable);

	List<LeadProject> findByOwner_IdIn(Set<String> userIds);

	Page<LeadProject> findByOwner_IdIn(Set<String> userIds, Pageable pageable);

	List<LeadProject> findByFollowupDateInAndCalledIsFalse(
			Set<String> followupDateSet, Boolean valueOf);

	List<LeadProject> findByLastModifiedDateBetweenAndCalledIsTrue(Date sdate,
			Date edate);

	List<LeadProject> findByLastModifiedDateBetween(Date sdate, Date edate);

	Page<LeadProject> findByLastModifiedDateBetween(Date sdate, Date edate,
			Pageable pageable);

	List<LeadProject> findByLastModifiedDateBetweenAndSalesExecutive_Id(
			Date sdate, Date edate, String fieldAssociateId);

	Page<LeadProject> findByLastModifiedDateBetweenAndSalesExecutive_Id(
			Date sdate, Date edate, String fieldAssociateId, Pageable pageable);

	LeadProject findByIdAndCreatedDateBetween(String id, Date sdate, Date edate);
}
