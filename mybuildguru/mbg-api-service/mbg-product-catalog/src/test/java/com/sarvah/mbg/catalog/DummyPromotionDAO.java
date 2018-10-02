/**
 * 
 */
package com.sarvah.mbg.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.dao.mongo.PromotionDAO;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.marketing.PromotionType;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu
 *
 */
public class DummyPromotionDAO implements PromotionDAO {

	@Override
	public List<Promotion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Promotion> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Promotion> findAll(Pageable arg0) {
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
	public void delete(Promotion arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Promotion> arg0) {
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
	public Iterable<Promotion> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promotion findOne(String pid) {
		// TODO Auto-generated method stub
		Promotion promotion=new Promotion();
		return promotion;
	}

	@Override
	public <S extends Promotion> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByDealer(User dealer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByDealer_Id(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByProductIdsIn(Collection<String> productIds) {
		// TODO Auto-generated method stub
		List<Promotion> promotionList = new ArrayList<>();
		Promotion promotion = new Promotion();
		promotionList.add(promotion);
		return promotionList;
	}

	@Override
	public List<Promotion> findByName(String name) {
		// TODO Auto-generated method stub
		List<Promotion> promotionList = new ArrayList<>();
		Promotion promotion = new Promotion();
		promotionList.add(promotion);
		return promotionList;
	}

	@Override
	public List<Promotion> findByType(PromotionType ptype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByDiscount(Double discount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByStartDate(Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Promotion> findByEndDate(Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByName(String name) {
		// TODO Auto-generated method stub
		return 10l;
	}

	@Override
	public long countByType(PromotionType promotionType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByDiscount(Double discount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByStartDate(Date startDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByEndDate(Date endDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByProductIdsIn(Set<String> productIds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promotion findByPromoCode(String promoCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Promotion> findByBrandsIn(
			Set<ProductBrand> prodBrandList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Promotion> findBySubCategoryIn(
			Set<SubCategory> prodSubCat) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.catalog.dao.mongo.PromotionDAO#findByName(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Promotion> findByName(String searchValue, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.catalog.dao.mongo.PromotionDAO#findByActive(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Promotion> findByActive(boolean boolean1, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.catalog.dao.mongo.PromotionDAO#findByNameAndActive(java.lang.String, boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Promotion> findByNameAndActive(String searchValue, boolean boolean1, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
