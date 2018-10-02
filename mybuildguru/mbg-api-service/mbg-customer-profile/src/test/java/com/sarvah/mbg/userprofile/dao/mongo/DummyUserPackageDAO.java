package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

public class DummyUserPackageDAO implements UserPackageDAO{

	@Override
	public <S extends UserPackage> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserPackage> findAll() {
		// TODO Auto-generated method stub
		UserPackage userPack=new UserPackage();
		userPack.setName("gold");
		Description desc=new Description("eng","gold package");
		userPack.setDesc(desc);
		List<UserPackage> userpackList=new ArrayList<>();
		userpackList.add(userPack);
		return userpackList;
	}

	@Override
	public List<UserPackage> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UserPackage> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UserPackage> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserPackage> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UserPackage> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPackage findOne(String id) {
		// TODO Auto-generated method stub
		UserPackage userPackage=new UserPackage();
		Description desc=new Description();
		userPackage.setDesc(desc);
		return userPackage;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<UserPackage> findAll(Iterable<String> ids) {
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
	public void delete(UserPackage entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends UserPackage> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserPackage findByName(String name) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
