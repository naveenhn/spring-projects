package com.sarvah.mbg.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

public class DummayProductTypeDAO implements ProductTypeDAO{

	@Override
	public List<ProductType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductType> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProductType> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProductType> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProductType> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductType> findAll(Pageable arg0) {
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
	public void delete(ProductType arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends ProductType> arg0) {
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
	public Iterable<ProductType> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductType findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProductType> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductType> findByName(String name) {
		// TODO Auto-generated method stub
		List<ProductType> productTypesList=new ArrayList();
		 ProductType productType1=new ProductType();
		 productType1.setName(name);
		productTypesList.add(productType1);
		return productTypesList;
	}

	@Override
	public Set<ProductType> findBySubCategory(SubCategory subCategory) {
		// TODO Auto-generated method stub
		return null;
	}

}
