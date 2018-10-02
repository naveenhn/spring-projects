/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;

/**
 * @author Shiva
 *
 */
public interface OrderStatusRepository extends
		CrudRepository<OrderStatus, Integer> {

	OrderStatus findByDescription(String description);
}
