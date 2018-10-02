/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;

/**
 * @author Shiva
 *
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findAll();

	List<Order> findByOrderStatus(OrderStatus orderstatus);
}
