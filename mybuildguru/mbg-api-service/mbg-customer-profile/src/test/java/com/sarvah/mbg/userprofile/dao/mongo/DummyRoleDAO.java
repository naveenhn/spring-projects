package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;

public class DummyRoleDAO implements RoleDAO {

	@Override
	public <S extends Role> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		List<Role> roleSet = new ArrayList<Role>();
		Role role = new Role();

		role.setName("Provider");

		UserPackage userPack = new UserPackage();
		userPack.setName("Silver");
		role.setUserPackage(userPack);

		Description desc1 = new Description("eng", "eng info");
		role.setDesc(desc1);
		// role.setDefaultPackages(defaultPack);

		roleSet.add(role);
		return roleSet;
	}

	@Override
	public List<Role> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Role> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Role> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Role> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findOne(String id) {
		// TODO Auto-generated method stub
		Role role=new Role();
		Description desc=new Description();
		desc.setLang("eng");
		desc.setVal("role information");
		role.setDesc(desc);
		UserPackage userPackage=new UserPackage();
		Description desc1=new Description();
		desc1.setLang("eng");
		desc1.setVal("role information");
		userPackage.setDesc(desc1);
		userPackage.setId("123");
		role.setUserPackage(userPackage);
		return role;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Role> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Role entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Role> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Role> findByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findByNameAllIgnoreCase(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
