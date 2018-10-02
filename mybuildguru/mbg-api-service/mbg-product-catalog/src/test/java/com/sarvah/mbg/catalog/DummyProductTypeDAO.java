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

import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author shivu
 *
 */
public class DummyProductTypeDAO implements ProductTypeDAO {

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
	public Page<ProductType> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProductType> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductType findOne(String id) {
		ProductType productType = new ProductType();
		productType.setName("Ivory");
		Description desc = new Description();
		desc.setLang("eng");
		desc.setVal("Ivory Info");
		productType.setDesc(desc);
		return productType;

	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ProductType> findAll(Iterable<String> ids) {
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
	public void delete(ProductType entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends ProductType> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductType> findByName(String name) {
		// TODO Auto-generated method stub
		List<ProductType> productTypeList = new ArrayList<>();
		ProductType productType = new ProductType();
		productTypeList.add(productType);
		return productTypeList;
	}

	@Override
	public Set<ProductType> findBySubCategory(SubCategory subCategory) {
		// TODO Auto-generated method stub
		ProductType productType1=new ProductType();
		Set<ProductType> productTypeList=new LinkedHashSet<>();
		productTypeList.add(productType1);
		return productTypeList;
	}
}
