package com.sarvah.mbg.project.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;

public interface ProjectDAO extends MongoRepository<Project, String> {
	Project findById(String projid);

	List<Project> findByNameAndStatusAllIgnoreCase(String name, String status);

	List<Project> findByBudget(Double budget);

	List<Project> findByTypeAndStatusAllIgnoreCase(ProjectType type,
			String status);

	List<Project> findByStatus(String status);

	List<Project> findByAddress_AddressLine1AndStatusAllIgnoreCase(
			String addrLine1, String status);

	List<Project> findByAddress_AddressLine2AndStatusAllIgnoreCase(
			String addressLine2, String status);

	List<Project> findByAddress_CityAndStatusAllIgnoreCase(String city,
			String status);

	List<Project> findByAddress_StateAndStatusAllIgnoreCase(String state,
			String status);

	List<Project> findByAddress_CountryAndStatusAllIgnoreCase(String country,
			String status);

	List<Project> findByAddress_ZipcodeAndStatusAllIgnoreCase(int zipcode,
			String status);

	List<Project> findByAddress_EmailAndStatusAllIgnoreCase(String email,
			String status);

	List<Project> findByUser_Id(String uid);

	List<Project> findByAddress_LocationAndStatusAllIgnoreCase(Point point,
			String status);

	List<Project> findByBudgetBetweenAndStatusAllIgnoreCase(double min,
			double max, String status);

	List<Project> findByBudgetBetweenAndTypeAndStatusAllIgnoreCase(double min,
			double max, String constructionType, String status);

	Set<Project> findByConstructionNewIsTrue(String projectType);

	Set<Project> findByConstructionNewIsFalse(String projectType);

	Set<Project> findByBudgetBetweenAndTypeAndConstructionNewIsTrue(double min,
			double max, String constructionType, String projectType);

	Set<Project> findByBudgetBetweenAndTypeAndConstructionNewIsFalse(
			double min, double max, String constructionType, String projectType);

	Set<Project> findByTypeAndConstructionNewIsTrue(String constructionType,
			String projectType);

	Set<Project> findByTypeAndConstructionNewIsFalse(String constructionType,
			String projectType);

	Set<Project> findByBudgetBetweenAndConstructionNewIsTrue(double min,
			double max, String projectType);

	Set<Project> findByBudgetBetweenAndConstructionNewIsFalse(double min,
			double max, String projectType);

	Set<Project> findByBudgetBetweenAndTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, String projectType,
			boolean constructionTypeVal, int zipcodeVal, String status);

	Set<Project> findByBudgetBetweenAndTypeAndConstructionNewAndStatusAllIgnoreCase(
			double min, double max, String projectType,
			boolean constructionTypeVal, String status);

	Set<Project> findByBudgetBetweenAndTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, String projectType, int zipcodeVal,
			String status);

	Set<Project> findByBudgetBetweenAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, boolean constructionTypeVal,
			int zipcodeVal, String status);

	Set<Project> findByTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			String projectType, boolean constructionTypeVal, int zipcodeVal,
			String status);

	Set<Project> findByBudgetBetweenAndConstructionNewAndStatusAllIgnoreCase(
			double min, double max, boolean constructionTypeVal, String status);

	Set<Project> findByBudgetBetweenAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, int zipcodeVal, String status);

	Set<Project> findByTypeAndConstructionNewAndStatusAllIgnoreCase(
			String projectType, boolean constructionTypeVal, String status);

	Set<Project> findByTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
			String projectType, int zipcodeVal, String status);

	Set<Project> findByConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			boolean constructionTypeVal, int zipcodeVal, String status);

	Set<Project> findByConstructionNewAndStatusAllIgnoreCase(
			boolean constructionTypeVal, String status);

	List<Project> findByStatusAllIgnoreCase(String string);
}
