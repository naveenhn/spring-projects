package com.sarvah.mbg.reviews.dao.mongo;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;

public interface UserOrderDetailRepository extends
		CrudRepository<OrderDetails, Integer> {

	OrderDetails findByOrderAndItemId(Order order, String itemid);

	OrderDetails findByOrderAndItemIdAndItemStatusDescription(Order order,
			String itemid, String itemstatus);
}
