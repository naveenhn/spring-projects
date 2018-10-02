package com.sarvah.mbg.catalog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;

public class DummaySubCategoryDAO implements SubCategoryDAO {

	@Override
	public List<SubCategory> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubCategory> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SubCategory> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SubCategory> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SubCategory> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SubCategory> findAll(Pageable arg0) {
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
	public void delete(SubCategory arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends SubCategory> arg0) {
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
	public Iterable<SubCategory> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubCategory findOne(String subcatid) {
		SubCategory subCategory = new SubCategory();
		subCategory.setName("Taps");
		Description desc = new Description();
		desc.setLang("eng");
		desc.setVal("SubCategory info");
		subCategory.setDesc(desc);
		return subCategory;
	}

	@Override
	public <S extends SubCategory> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubCategory> findByName(String name) {
		// TODO Auto-generated method stub

		List<SubCategory> subCategoryList = new ArrayList<>();
		SubCategory subCategory1 = new SubCategory();
		subCategory1.setName(name);
		subCategoryList.add(subCategory1);
		return subCategoryList;
	}

	@Override
	public Set<SubCategory> findByCategory(String category) {
		SubCategory subcategory = new SubCategory();
		subcategory.setName("LEDBulbs");
		Set<SubCategory> subCategoriesList = new LinkedHashSet<>();
		subCategoriesList.add(subcategory);
		return subCategoriesList;
	}

	@Override
	public SubCategory findById(String subcatid) {
		// TODO Auto-generated method stub
		return null;
	}
}
