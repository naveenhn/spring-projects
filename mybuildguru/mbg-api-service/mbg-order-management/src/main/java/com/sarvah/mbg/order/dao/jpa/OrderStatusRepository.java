/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;

/**
 * @author shivu
 *
 */
public interface OrderStatusRepository extends CrudRepository<OrderStatus,String> {

	OrderStatus findByDescription(String status);

}
