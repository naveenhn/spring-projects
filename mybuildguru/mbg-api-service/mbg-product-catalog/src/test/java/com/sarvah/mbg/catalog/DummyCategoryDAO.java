package com.sarvah.mbg.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.common.Description;

public class DummyCategoryDAO implements CategoryDAO {

	@Override
	public List<Category> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		List<Category> categoryList = new ArrayList<>();
		Category category1 = new Category();
		Category category2 = new Category();
		Category category3 = new Category();
		categoryList.add(category1);
		categoryList.add(category2);
		categoryList.add(category3);
		System.out.println(categoryList);
		return categoryList;
	}

	@Override
	public <S extends Category> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Category> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		Category category = new Category();
		return null;
	}

	@Override
	public Page<Category> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 20l;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Category arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Category> arg0) {
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
	public Iterable<Category> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findOne(String categoryid) {
		Category category = new Category();
		category.setId("54321");
		category.setName("abc");
		Description desc = new Description();
		desc.setLang("en");
		desc.setVal("Bath tub info");
		category.setDesc(desc);
		return category;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		List<Category> categoryList = new ArrayList<>();
		Category category1 = new Category();
		Category category2 = new Category();
		Category category3 = new Category();
		categoryList.add(category1);
		categoryList.add(category2);
		categoryList.add(category3);
		return categoryList;
	}

	@Override
	public Category insert(Category category) {
		// TODO Auto-generated method stub
		Category category1 = new Category();
		Description desc = new Description();
		desc.setLang(category.getDesc().getLang());
		desc.setVal(category.getDesc().getVal());
		category1.setDesc(desc);
		return category1;
	}

	@Override
	public Category findById(String catid) {
		// TODO Auto-generated method stub
		Category category = new Category();
		category.setId(catid);
		return category;
	}

	@Override
	public long deleteById(String catid) {
		// TODO Auto-generated method stub
		return Long.parseLong(catid);
	}

	@Override
	public <S extends Category> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByName(String name) {
		// TODO Auto-generated method stub
		return 10l;
	}

	@Override
	public List<Category> findByNameAllIgnoreCase(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
