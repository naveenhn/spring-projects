/**
 * 
 */
package com.sarvah.mbg.store.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.common.UITemplate;

/**
 * @author Shiva
 *
 */
public interface UITemplateDAO extends MongoRepository<UITemplate,String>{

}
