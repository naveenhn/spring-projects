/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu s
 *
 */
public interface PromotionBannerDAO extends MongoRepository<Banner,String> {
	
	List<Banner> findByUser(User user);
	
	long countByUser(User user);

}
