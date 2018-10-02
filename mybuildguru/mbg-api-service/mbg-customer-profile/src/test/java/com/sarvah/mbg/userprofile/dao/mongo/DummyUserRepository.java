/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;

/**
 * @author shivu
 *
 */
public class DummyUserRepository implements UserRepository{

	@Override
	public <S extends UserInfo> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends UserInfo> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findOne(Integer id) {
		UserInfo userInfo=new UserInfo();
		userInfo.setUserid(123);
		return userInfo;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<UserInfo> findAll(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UserInfo entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends UserInfo> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserInfo findByUsername(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findByMongoUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findByUserid(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findByUsernameAndPhonenumber(String userName, Long phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo findByPhonenumber(Long phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}
}
