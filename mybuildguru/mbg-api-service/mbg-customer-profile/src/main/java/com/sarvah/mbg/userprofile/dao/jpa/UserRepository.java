/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author shivu
 *
 */
public interface UserRepository extends CrudRepository<UserInfo,Integer> {

	UserInfo findByUsername(String userName);
	UserInfo findByPhonenumber(Long phoneNumber);
	UserInfo findByUsernameAndPhonenumber(String userName, Long phoneNumber);
	UserInfo findByMongoUserId(String userId);
	UserInfo findByUserid(int userId);
}
