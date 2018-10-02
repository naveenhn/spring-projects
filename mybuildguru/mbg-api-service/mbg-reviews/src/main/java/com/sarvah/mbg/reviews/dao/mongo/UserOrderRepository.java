package com.sarvah.mbg.reviews.dao.mongo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * 
 * @author RAJU
 *
 */

public interface UserOrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByUserInfo(UserInfo userInfo);

}
