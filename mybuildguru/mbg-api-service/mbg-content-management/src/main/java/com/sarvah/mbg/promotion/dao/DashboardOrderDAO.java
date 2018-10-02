package com.sarvah.mbg.promotion.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * 
 * @author Raju
 *
 */
public interface DashboardOrderDAO extends CrudRepository<Order, Integer> {

	// long findByItemStatus_DescriptionIsLike(String status);
	Set<Order> findByUserInfo_Userid(int id);

	Set<Order> findByUserInfo(UserInfo userInfo);
}
