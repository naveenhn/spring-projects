/**
 * 
 */
package com.sarvah.mbg.ordermgmt;

import java.util.List;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.order.dao.jpa.OrderDetailsRepository;

/**
 * @author shivu
 *
 */
public class DummyOrderDetailsRepository implements OrderDetailsRepository {

	@Override
	public <S extends OrderDetails> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends OrderDetails> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void delete(OrderDetails entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends OrderDetails> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderDetails> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Set<OrderDetails> findByOrder(Order order) {
//		Set<OrderDetails> orderDetails=new HashSet<>();
//		OrderDetails orderDetail1=new OrderDetails();
//		orderDetail1.setItemId("1");
//		orderDetail1.setItemName("trimmer");
//		ItemStatus itemStatus=new ItemStatus();
//		itemStatus.setItemStatusId(1);
//		orderDetail1.setItemStatus(itemStatus);
//		orderDetail1.setMrp(1000);
//		orderDetail1.setQuantity(1);
//		orderDetail1.setSellingPrice(750);
//		orderDetail1.setShippingCharge(50);
//		orderDetail1.setTax(10);
//		orderDetail1.setTotalPrice(880);
//		orderDetail1.setDealerId("123");
//		orderDetails.add(orderDetail1);
//		return orderDetails;
//	}

	@Override
	public OrderDetails findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<OrderDetails> findAll(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDetails findByOrderAndItemId(Order order, String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteByItemId(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteByItemIdAndOrderDetailsId(int orderDetailsId,
			String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteByOrderDetailsId(int orderDetailsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDetails> findByOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}



}
