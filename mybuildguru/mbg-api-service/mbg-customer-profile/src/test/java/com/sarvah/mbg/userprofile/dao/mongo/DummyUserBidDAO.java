package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public class DummyUserBidDAO implements UserBidDAO {

	@Override
	public List<Bid> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bid> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Bid> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Bid> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Bid> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Bid> findAll(Pageable arg0) {
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
	public void delete(Bid arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Bid> arg0) {
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
	public Iterable<Bid> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bid findOne(String arg0) {
		// TODO Auto-generated method stub
		return new Bid();
	}

	@Override
	public <S extends Bid> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Bid> findByUser(User user) {
		// TODO Auto-generated method stub
		List<Bid> bidList=new ArrayList<>();
		Bid bid=new Bid();
		bidList.add(bid);
		return bidList;
	}

	@Override
	public Long countByUser(User user) {
		// TODO Auto-generated method stub
		return 10l;
	}

}
