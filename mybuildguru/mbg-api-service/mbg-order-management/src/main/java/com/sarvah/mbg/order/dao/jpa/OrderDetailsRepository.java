/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;

/**
 * @author shivu
 *
 */
public interface OrderDetailsRepository extends
		CrudRepository<OrderDetails, Integer> {
	List<OrderDetails> findAll();

	List<OrderDetails> findByOrder(Order order);

	OrderDetails findByOrderAndItemId(Order order, String itemId);

	String deleteByItemId(String itemId);

	String deleteByItemIdAndOrderDetailsId(int orderDetailsId, String itemId);

	String deleteByOrderDetailsId(int orderDetailsId);
}
