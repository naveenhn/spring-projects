/**
 * 
 */
package com.sarvah.mbg.recentlyviewed.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;


/**
 * @author shivu s
 *
 */
public class DummyRecentlyViewedDAO implements RecentlyViewedDAO {
	
	private RecentlyViewed recentlyViewed;

	@Override
	public <S extends RecentlyViewed> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecentlyViewed> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecentlyViewed> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends RecentlyViewed> S insert(S entity) {
		recentlyViewed = entity;
		return (S) recentlyViewed;
	}

	@Override
	public <S extends RecentlyViewed> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<RecentlyViewed> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends RecentlyViewed> S save(S entity) {
		recentlyViewed = entity;
		return (S) recentlyViewed;
	}

	@Override
	public RecentlyViewed findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String id) {
		if(recentlyViewed == null)
		return false;
		else 
			return true;
	}

	@Override
	public Iterable<RecentlyViewed> findAll(Iterable<String> ids) {
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
	public void delete(RecentlyViewed entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends RecentlyViewed> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RecentlyViewed findByUser_Id(String userId) {
		//recentlyViewed.setId("1");
		return recentlyViewed;
	}

	@Override
	public String deleteByProductIds(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
