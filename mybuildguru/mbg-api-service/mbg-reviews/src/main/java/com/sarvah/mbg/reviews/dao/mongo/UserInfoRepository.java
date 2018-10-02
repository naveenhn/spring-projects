package com.sarvah.mbg.reviews.dao.mongo;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * 
 * @author RAJU
 *
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {

	UserInfo findByMongoUserId(String uid);

}
