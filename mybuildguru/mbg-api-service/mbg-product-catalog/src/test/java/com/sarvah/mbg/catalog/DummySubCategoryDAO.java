/**
 * 
 */
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

/**
 * @author shivu
 *
 */
public class DummySubCategoryDAO implements SubCategoryDAO {

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
	public Page<SubCategory> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SubCategory> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubCategory findOne(String id) {
		// TODO Auto-generated method stub
		SubCategory subCategory = new SubCategory();
		subCategory.setName("Taps");
		Description desc = new Description();
		desc.setLang("eng");
		desc.setVal("SubCategory info");
		subCategory.setDesc(desc);
		return subCategory;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<SubCategory> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(SubCategory entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends SubCategory> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SubCategory> findByName(String name) {
		// TODO Auto-generated method stub
		List<SubCategory> subCategoryList = new ArrayList<>();
		SubCategory subCategory = new SubCategory();
		subCategoryList.add(subCategory);
		return subCategoryList;
	}

	@Override
	public SubCategory findById(String subcatid) {
		SubCategory subCategory = new SubCategory();
		subCategory.setId("124");
		return subCategory;
	}

	@Override
	public Set<SubCategory> findByCategory(String category) {
		// TODO Auto-generated method stub
		SubCategory subCategory = new SubCategory();
		subCategory.setName("tap");
		Set<SubCategory> subCategoryList = new LinkedHashSet<>();
		subCategoryList.add(subCategory);
		return subCategoryList;
	}

}
