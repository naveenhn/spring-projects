/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;

/**
 * @author Shiva
 *
 */
public interface OrderDetailsRepository extends
		CrudRepository<OrderDetails, Integer> {
	Set<OrderDetails> findByOrder(Order order);
	
	Set<OrderDetails> findByOrderAndDealerId(Order order,String dealerId);
}
