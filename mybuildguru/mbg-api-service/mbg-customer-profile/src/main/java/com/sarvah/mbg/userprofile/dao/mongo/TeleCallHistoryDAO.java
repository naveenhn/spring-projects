/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.TeleCallHistory;

/**
 * @author Shivu
 *
 */
public interface TeleCallHistoryDAO extends
		MongoRepository<TeleCallHistory, String> {

	TeleCallHistory findByTeleIdAndCreatedDateBetween(String id, Date sdate,
			Date edate);

}
