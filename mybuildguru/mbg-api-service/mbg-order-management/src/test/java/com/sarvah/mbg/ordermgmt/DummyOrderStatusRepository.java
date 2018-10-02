/**
 * 
 */
package com.sarvah.mbg.ordermgmt;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.order.dao.jpa.OrderStatusRepository;

/**
 * @author shivu
 *
 */
public class DummyOrderStatusRepository implements OrderStatusRepository {

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
	public OrderStatus findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<OrderStatus> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<OrderStatus> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
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
	public OrderStatus findByDescription(String status) {
		// TODO Auto-generated method stub
		return null;
	}

}
