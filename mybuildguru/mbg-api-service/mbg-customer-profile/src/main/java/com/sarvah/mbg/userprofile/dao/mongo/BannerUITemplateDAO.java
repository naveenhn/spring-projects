/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.common.UITemplate;

/**
 * @author shivu s
 *
 */
public interface BannerUITemplateDAO extends
		MongoRepository<UITemplate, String> {

	UITemplate findByName(String name);
}
