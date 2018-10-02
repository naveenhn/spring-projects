/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.marketing.PromotionType;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu
 *
 */
public interface PromotionDAO extends MongoRepository<Promotion, String> {
	List<Promotion> findByDealer(User dealer);

	List<Promotion> findByDealer_Id(String userId);

	List<Promotion> findByProductIdsIn(Collection<String> productIds);

	List<Promotion> findByName(String name);

	List<Promotion> findByType(PromotionType ptype);

	List<Promotion> findByDiscount(Double discount);

	List<Promotion> findByStartDate(Date startDate);

	List<Promotion> findByEndDate(Date endDate);

	long countByName(String name);

	long countByType(PromotionType promotionType);

	long countByDiscount(Double discount);

	long countByStartDate(Date startDate);

	long countByEndDate(Date endDate);

	long countByProductIdsIn(Set<String> productIds);

	List<User> findById(String id);

	Promotion findByPromoCode(String promoCode);

	Collection<? extends Promotion> findByBrandsIn(
			Set<ProductBrand> prodBrandList);

	Collection<? extends Promotion> findBySubCategoryIn(
			Set<SubCategory> prodSubCat);

	Page<Promotion> findByName(String searchValue, Pageable pageable);

	Page<Promotion> findByActive(boolean boolean1, Pageable pageable);

	Page<Promotion> findByNameAndActive(String searchValue, boolean boolean1,
			Pageable pageable);
}
