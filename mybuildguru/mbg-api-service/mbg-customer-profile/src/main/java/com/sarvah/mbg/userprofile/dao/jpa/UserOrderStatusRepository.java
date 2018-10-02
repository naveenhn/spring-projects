/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;

/**
 * @author shivu
 *
 */
public interface UserOrderStatusRepository extends CrudRepository<OrderStatus,Integer>{
	OrderStatus findByDescription(String orderStatus);
	OrderStatus findByOrderStatusId(int orderStatusId);
}
