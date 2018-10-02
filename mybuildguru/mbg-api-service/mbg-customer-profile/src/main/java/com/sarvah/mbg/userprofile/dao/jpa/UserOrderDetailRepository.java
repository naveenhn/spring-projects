/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;

/**
 * @author shivu
 *
 */
public interface UserOrderDetailRepository extends
		CrudRepository<OrderDetails, Integer> {
	List<OrderDetails> findByOrder(Order order);

	OrderDetails findByOrderAndOrderDetailsId(Order order, int itemid);

	List<OrderDetails> findByOrderAndDealerId(Order order, String uid);

	List<OrderDetails> findByOrderAndItemStatus_Description(Order order,
			String string);

	List<OrderDetails> findByDealerId(String uid);

	Page<OrderDetails> findByDealerId(String uid, Pageable pageable);

	List<OrderDetails> findByItemId(String pid);
}
