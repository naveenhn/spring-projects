/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderAddressRepository;

/**
 * @author shivu
 *
 */
public class DummyUserOrderAddressRepository implements UserOrderAddressRepository{

	@Override
	public <S extends OrderAddress> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends OrderAddress> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderAddress findOne(Integer id) {
		OrderAddress billingAddress=new OrderAddress();
		billingAddress.setAddressId(1);
		billingAddress.setAddressLine1("#24 mysore");
		billingAddress.setAddressLine2("near to bus stop");
		billingAddress.setCity("mysore");
		billingAddress.setState("karnataka");
		billingAddress.setCountry("india");
		billingAddress.setContactNo("9645785421");
		billingAddress.setPincode(571114);
		return billingAddress;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<OrderAddress> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<OrderAddress> findAll(Iterable<Integer> ids) {
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
	public void delete(OrderAddress entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends OrderAddress> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
