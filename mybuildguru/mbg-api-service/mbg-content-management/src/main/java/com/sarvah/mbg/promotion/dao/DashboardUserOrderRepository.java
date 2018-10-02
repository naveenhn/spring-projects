/**
 * 
 */
package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author Shiva
 *
 */
public interface DashboardUserOrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByUserInfo(UserInfo userInfo);

}
