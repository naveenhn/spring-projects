/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderStatusRepository;

/**
 * @author shivu
 *
 */
public class DummyUserOrderStatusRepository implements UserOrderStatusRepository{

	@Override
	public <S extends OrderStatus> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends OrderStatus> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderStatus findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<OrderStatus> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<OrderStatus> findAll(Iterable<Integer> ids) {
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
	public void delete(OrderStatus entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends OrderStatus> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderStatus findByDescription(String orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderStatus findByOrderStatusId(int orderStatusId) {
		// TODO Auto-generated method stub
		return null;
	}

}
