package com.sarvah.mbg.reviews.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;

/**
 * 
 * @author RAJU
 *
 */

public interface ReviewUserOrderRepository extends CrudRepository<Order, Integer> {

	//List<Order> findByUserInfo(UserInfo userInfo);
	List<Order> findByUserInfo_MongoUserId(String mongoUserId);

}
