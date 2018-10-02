package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderDetailRepository;

public class DummyUserOrderDetailRepository implements
		UserOrderDetailRepository {

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Integer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(OrderDetails arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends OrderDetails> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Integer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<OrderDetails> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<OrderDetails> findAll(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDetails findOne(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends OrderDetails> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends OrderDetails> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDetails findByOrderAndOrderDetailsId(Order order, int itemid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByOrderAndItemStatus_Description(Order order,
			String string) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.jpa.UserOrderDetailRepository#findByDealerId
	 * (java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<OrderDetails> findByDealerId(String uid, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByItemId(String pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByOrderAndDealerId(Order order, String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByDealerId(String uid) {
		// TODO Auto-generated method stub
		return null;
	}
}
