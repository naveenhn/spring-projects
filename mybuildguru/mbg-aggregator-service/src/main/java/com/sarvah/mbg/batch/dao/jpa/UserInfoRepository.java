package com.sarvah.mbg.batch.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo,Integer>{

	UserInfo findByUsername(String userName);

}
