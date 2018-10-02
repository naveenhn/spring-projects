package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;

public class DummyWishListDAO implements WishListDAO {

	@Override
	public List<WishList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WishList> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends WishList> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends WishList> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends WishList> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<WishList> findAll(Pageable arg0) {
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
	public void delete(WishList arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends WishList> arg0) {
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
	public Iterable<WishList> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WishList findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends WishList> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WishList findByUser_Id(String uid) {
		// TODO Auto-generated method stub
		WishList wishList=new WishList();
		wishList.setId("123");
		Set<String> productIds = new HashSet<>();
		productIds.add("1");
		productIds.add("2");
		wishList.setProductIds(productIds);
		return wishList;
	}

	@Override
	public String deleteByProductIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WishList findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
