//package com.sarvah.mbg.ordermgmt;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
//import com.sarvah.mbg.order.service.OrderService;
//import com.sarvah.mbg.ordermgmt.response.GetOrdersResponseParent;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MbgOrderManagementTestConfiguration.class)
//public class MbgOrderManagementApplicationTests {
//
//	@Autowired
//	OrderService orderService;
//
//	@Test
//	public void getAddress() throws Exception {
//		List<OrderAddress> orderAddress = orderService.getAddress(1);
//
//		assertTrue(orderAddress.size() == 2);
//
//		assertFalse(orderAddress.size() == 1);
//
//		assertEquals(orderAddress.get(0).getAddressLine1(), "#14 mysore");
//		assertNotEquals(orderAddress.get(0).getAddressLine1(), "#20 mysore");
//		assertEquals(orderAddress.get(0).getAddressLine2(), "near to bus stop");
//		assertNotEquals(orderAddress.get(0).getAddressLine2(), "near to water tank");
//		assertEquals(orderAddress.get(0).getCity(), "mysore");
//		assertNotEquals(orderAddress.get(0).getCity(), "banglore");
//		assertEquals(orderAddress.get(0).getState(), "karnataka");
//		assertNotEquals(orderAddress.get(0).getState(), "tamilnadu");
//		assertEquals(orderAddress.get(0).getCountry(), "india");
//		assertNotEquals(orderAddress.get(0).getCountry(), "astralia");
//		assertEquals(orderAddress.get(0).getPincode(), 571114);
//		assertNotEquals(orderAddress.get(0).getPincode(), 571667);
//		assertEquals(orderAddress.get(0).getContactNo(), "9611652256");
//		assertNotEquals(orderAddress.get(0).getContactNo(), "8511652256");
//
//		assertEquals(orderAddress.get(1).getAddressLine1(), "#15 mysore");
//		assertNotEquals(orderAddress.get(1).getAddressLine1(), "#20 mysore");
//		assertEquals(orderAddress.get(1).getAddressLine2(), "near to bus stop");
//		assertNotEquals(orderAddress.get(1).getAddressLine2(), "near to water tank");
//		assertEquals(orderAddress.get(1).getCity(), "mysore");
//		assertNotEquals(orderAddress.get(1).getCity(), "banglore");
//		assertEquals(orderAddress.get(1).getState(), "karnataka");
//		assertNotEquals(orderAddress.get(1).getState(), "tamilnadu");
//		assertEquals(orderAddress.get(1).getCountry(), "india");
//		assertNotEquals(orderAddress.get(1).getCountry(), "astralia");
//		assertEquals(orderAddress.get(1).getPincode(),571225);
//		assertNotEquals(orderAddress.get(1).getPincode(), 571667);
//		assertEquals(orderAddress.get(1).getContactNo(), "9661652278");
//		assertNotEquals(orderAddress.get(1).getContactNo(), "9661652281");
//		assertNotEquals("","");
//	}
//	
//	@Test
//	public void getOrder() throws Exception{
//		List<GetOrdersResponseParent> orders=orderService.getOrder();
//		assertTrue(orders.size()==2);
//		assertFalse(orders.size()==1);
//	}
//	
////	@Test
////	public void getItems(){
////		Set<OrderDetails> orderDetails=orderService.getItems(1);
////		assertTrue(orderDetails.size()==1);
////	}
//
//}
