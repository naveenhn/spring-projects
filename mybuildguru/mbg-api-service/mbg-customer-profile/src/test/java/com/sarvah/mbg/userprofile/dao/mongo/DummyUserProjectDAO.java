package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public class DummyUserProjectDAO implements UserProjectDAO {

	@Override
	public long countByUser(User user) {
		// TODO Auto-generated method stub
		return 60l;
	}

	@Override
	public long countByNameAndUser(String name, User user) {
		// TODO Auto-generated method stub
		return 10l;
	}

	@Override
	public long countByTypeAndUser(ProjectType type, User user) {
		// TODO Auto-generated method stub
		return 50l;
	}

	@Override
	public long countByAddress_CityAndUser(String city, User user) {
		// TODO Auto-generated method stub
		return 20l;
	}

	@Override
	public long countByAddress_AddressLine1LikeAndUser(String addrLine1,
			User user) {
		// TODO Auto-generated method stub
		return 30l;
	}

	@Override
	public long countByAddress_AddressLine2LikeAndUser(String addrLine2,
			User user) {
		// TODO Auto-generated method stub
		return 40l;
	}

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
		Project project = new Project();
		Address addr = new Address();
		project.setAddress(addr);
		return project;
	}

	@Override
	public <S extends Project> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByNameAndUser(String name, User user) {

		List<Project> projectSet = new ArrayList<Project>();
		Project project = new Project();

		project.setName("MyBuilsGuru");
		project.setBudget(500000.22);
		project.setType(ProjectType.RESIDENTIAL);

		Description desc1 = new Description("eng", "Housing project");
		project.setDesc(desc1);

		Address address = new Address();
		address.setAddressId(1);
		address.setAddressLine1("ramakrishnanagar");
		address.setAddressLine2("mysore");
		address.setCity("mysore");
		address.setCountry("india");
		address.setZipcode(570022);
		address.setEmail("abc@gmail.com");

		project.setAddress(address);

		projectSet.add(project);
		return projectSet;
	}

	@Override
	public List<Project> findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByTypeAndUser(String type, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_AddressLine1AndUser(String addrLine1,
			User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_AddressLine2AndUser(String addrLine2,
			User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_CityAndUser(String city, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_ZipcodeAndUser(Integer zipcode, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findByAddress_Location(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByUserId(String uid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Project findByIdAndUser(String uid, User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
