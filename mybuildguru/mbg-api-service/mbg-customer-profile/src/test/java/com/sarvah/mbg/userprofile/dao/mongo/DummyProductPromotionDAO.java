/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu s
 *
 */
public class DummyProductPromotionDAO implements ProductPromotionDAO {

	@Override
	public <S extends Promotion> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Promotion> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promotion findOne(String id) {
		Promotion promotion=new Promotion();
		Description desc=new Description();
		desc.setLang("eng");
		desc.setVal("promotion info");
		promotion.setDesc(desc);
		return promotion;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Promotion> findAll(Iterable<String> ids) {
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
	public void delete(Promotion entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Promotion> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Promotion> findByDealer(User user) {
		// TODO Auto-generated method stub
		List<Promotion> promotions=new ArrayList<>();
		Promotion promo=new Promotion();
		promo.setName("Deepavali Promotion");
		promotions.add(promo);
		Promotion promo1=new Promotion();
		promo1.setName("Sunday Promotion");
		promotions.add(promo1);
		return promotions;
	}

	@Override
	public long countByDealer(User user) {
		// TODO Auto-generated method stub
		return 10l;
	}

}
