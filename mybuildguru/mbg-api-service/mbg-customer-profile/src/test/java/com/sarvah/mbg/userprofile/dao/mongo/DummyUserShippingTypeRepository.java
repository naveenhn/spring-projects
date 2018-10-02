/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.userprofile.dao.jpa.UserShippingTypeRepository;

/**
 * @author shivu
 *
 */
public class DummyUserShippingTypeRepository implements UserShippingTypeRepository{

	@Override
	public <S extends ShippingType> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ShippingType> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShippingType findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ShippingType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShippingType> findAll(Iterable<Integer> ids) {
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
	public void delete(ShippingType entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends ShippingType> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ShippingType findByShippingType(String shippingType) {
		// TODO Auto-generated method stub
		return null;
	}

}
