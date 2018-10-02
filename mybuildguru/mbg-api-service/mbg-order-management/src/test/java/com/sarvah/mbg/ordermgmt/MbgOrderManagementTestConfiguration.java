/**
 * 
 */
package com.sarvah.mbg.ordermgmt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.order.dao.jpa.OrderDetailsRepository;
import com.sarvah.mbg.order.dao.jpa.OrderRepository;
import com.sarvah.mbg.order.dao.jpa.OrderStatusRepository;
import com.sarvah.mbg.order.dao.jpa.ShippingTypeRepository;
import com.sarvah.mbg.order.service.OrderService;
import com.sarvah.mbg.order.service.impl.OrderServiceImpl;

/**
 * @author shivu
 *
 */
@Configuration
public class MbgOrderManagementTestConfiguration {
	@Bean
	public OrderService orderService(){
		return new OrderServiceImpl();
	}
	
	@Bean
	public OrderRepository orderRepository(){
		return new DummyOrderRepository();
	}
	
	@Bean 
	public ShippingTypeRepository shippingTypeRepository(){
		return new DummyShippingTypeRepository();
	}
	
	@Bean
	public OrderStatusRepository orderStatusRepository(){
		return new DummyOrderStatusRepository();
	}
	@Bean
	public OrderDetailsRepository orderDetailsRepository(){
		return new DummyOrderDetailsRepository();
	}
}
