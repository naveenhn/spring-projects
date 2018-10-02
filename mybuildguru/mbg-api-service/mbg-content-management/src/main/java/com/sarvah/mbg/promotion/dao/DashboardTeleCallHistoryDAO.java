/**
 * 
 */
package com.sarvah.mbg.promotion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.TeleCallHistory;

/**
 * @author Shivu
 *
 */
public interface DashboardTeleCallHistoryDAO extends
		MongoRepository<TeleCallHistory, String> {

	List<TeleCallHistory> findByTeleIdAndCreatedDateBetween(String id,
			Date sdate, Date edate);

}
