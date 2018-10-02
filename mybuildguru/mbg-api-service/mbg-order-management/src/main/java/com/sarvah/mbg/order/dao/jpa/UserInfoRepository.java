/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author naveen
 *
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {


	UserInfo findByUsername(String userName);


}
