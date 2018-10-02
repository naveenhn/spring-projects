/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu s
 *
 */
public class DummyBannerDAO implements PromotionBannerDAO {

	@Override
	public <S extends Banner> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Banner> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Banner> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Banner> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Banner> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Banner> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Banner> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Banner findOne(String id) {
		// TODO Auto-generated method stub
		Banner banner = new Banner();
		banner.setId("1");
		return banner;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Banner> findAll(Iterable<String> ids) {
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
	public void delete(Banner entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Banner> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Banner> findByUser(User user) {
		// TODO Auto-generated method stub
		List<Banner> bannerList = new ArrayList<Banner>();
		Banner banner = new Banner();
		bannerList.add(banner);
		return bannerList;
	}

	@Override
	public long countByUser(User user) {
		// TODO Auto-generated method stub
		return 10;
	}

}
