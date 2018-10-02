/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author shivu
 *
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
	List<Order> findAll();

	Order findByMbgOrderId(String orderId);

	Order findByOrderId_AndUserInfo(int orderid, UserInfo userInfo);

	int deleteByOrderId(int orderId);

	void deleteByMbgOrderId(String orderId);

}
