/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author Shivu
 *
 */
public interface UserRepo extends CrudRepository<UserInfo, Integer> {
	List<UserInfo> findByUsername(String userName);

	List<UserInfo> findByPhonenumber(Long phoneNumber);
}
