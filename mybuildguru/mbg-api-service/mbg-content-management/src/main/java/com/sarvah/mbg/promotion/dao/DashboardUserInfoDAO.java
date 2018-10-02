package com.sarvah.mbg.promotion.dao;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * 
 * @author Raju
 *
 */
public interface DashboardUserInfoDAO extends CrudRepository<UserInfo, Integer> {

	UserInfo findByMongoUserId(String id);
}
