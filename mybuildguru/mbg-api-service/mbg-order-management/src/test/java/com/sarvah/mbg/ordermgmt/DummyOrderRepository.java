/**
 * 
 */
package com.sarvah.mbg.ordermgmt;

import java.util.ArrayList;
import java.util.List;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.order.dao.jpa.OrderRepository;

/**
 * @author shivu
 *
 */
public class DummyOrderRepository implements OrderRepository {

	@Override
	public <S extends Order> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Order> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOne(Integer id) {
		Order order = new Order();
		OrderAddress billingAddress = new OrderAddress();
		billingAddress.setAddressLine1("#14 mysore");
		billingAddress.setAddressLine2("near to bus stop");
		billingAddress.setCity("mysore");
		billingAddress.setState("karnataka");
		billingAddress.setCountry("india");
		billingAddress.setPincode(571114);
		billingAddress.setContactNo("9611652256");

		order.setBillingAddress(billingAddress);

		OrderAddress shippingAddress = new OrderAddress();

		shippingAddress.setAddressLine1("#15 mysore");
		shippingAddress.setAddressLine2("near to bus stop");
		shippingAddress.setCity("mysore");
		shippingAddress.setState("karnataka");
		shippingAddress.setCountry("india");
		shippingAddress.setPincode(571225);
		shippingAddress.setContactNo("9661652278");
		order.setShippingAddress(shippingAddress);
		return order;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Order> findAll(Iterable<Integer> ids) {
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
	public void delete(Order entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Order> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> findAll() {
		List<Order> orders = new ArrayList<>();
		Order order1 = new Order();
		order1.setOrderId(1);
		orders.add(order1);

		Order order2 = new Order();
		order2.setOrderId(2);
		orders.add(order2);

		return orders;
	}

	@Override
	public Order findByOrderId_AndUserInfo(int orderid, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order findByMbgOrderId(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByMbgOrderId(String orderId) {
		// TODO Auto-generated method stub
		
	}

}
