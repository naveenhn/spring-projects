package com.sarvah.mbg.project.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;

public class DummyProjectDAO implements ProjectDAO {

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Project> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Project> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Project> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Project> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Project arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Project> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Project> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findOne(String arg0) {
		// TODO Auto-generated method stub
		return new Project();
	}

	@Override
	public <S extends Project> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByBudget(Double budget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByConstructionNewIsTrue(String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByConstructionNewIsFalse(String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByTypeAndConstructionNewIsTrue(
			String constructionType, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByTypeAndConstructionNewIsFalse(
			String constructionType, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndTypeAndConstructionNewIsTrue(
			double min, double max, String constructionType, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndTypeAndConstructionNewIsFalse(
			double min, double max, String constructionType, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndConstructionNewIsTrue(double min,
			double max, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndConstructionNewIsFalse(
			double min, double max, String projectType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByStatusAllIgnoreCase(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findById(String projid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByNameAndStatusAllIgnoreCase(String name,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByTypeAndStatusAllIgnoreCase(ProjectType type,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_AddressLine1AndStatusAllIgnoreCase(
			String addrLine1, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_AddressLine2AndStatusAllIgnoreCase(
			String addressLine2, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_CityAndStatusAllIgnoreCase(String city,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_StateAndStatusAllIgnoreCase(
			String state, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_CountryAndStatusAllIgnoreCase(
			String country, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_ZipcodeAndStatusAllIgnoreCase(
			int zipcode, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_EmailAndStatusAllIgnoreCase(
			String email, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByUser_Id(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_LocationAndStatusAllIgnoreCase(
			Point point, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByBudgetBetweenAndStatusAllIgnoreCase(double min,
			double max, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByBudgetBetweenAndTypeAndStatusAllIgnoreCase(
			double min, double max, String constructionType, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, String projectType,
			boolean constructionTypeVal, int zipcodeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndTypeAndConstructionNewAndStatusAllIgnoreCase(
			double min, double max, String projectType,
			boolean constructionTypeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, String projectType, int zipcodeVal,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, boolean constructionTypeVal,
			int zipcodeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			String projectType, boolean constructionTypeVal, int zipcodeVal,
			String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndConstructionNewAndStatusAllIgnoreCase(
			double min, double max, boolean constructionTypeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByBudgetBetweenAndAddress_ZipcodeAndStatusAllIgnoreCase(
			double min, double max, int zipcodeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByTypeAndConstructionNewAndStatusAllIgnoreCase(
			String projectType, boolean constructionTypeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
			String projectType, int zipcodeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
			boolean constructionTypeVal, int zipcodeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findByConstructionNewAndStatusAllIgnoreCase(
			boolean constructionTypeVal, String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
