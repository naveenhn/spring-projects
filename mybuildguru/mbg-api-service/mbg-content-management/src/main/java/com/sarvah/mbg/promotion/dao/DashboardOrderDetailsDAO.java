package com.sarvah.mbg.promotion.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;

public interface DashboardOrderDetailsDAO extends  CrudRepository<OrderDetails,Integer> {
	
	Set<OrderDetails> findByDealerId(String dealerId);

	Set<OrderDetails> findByOrder(Order order);
	

}
