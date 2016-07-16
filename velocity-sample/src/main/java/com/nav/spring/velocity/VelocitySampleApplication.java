package com.nav.spring.velocity;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.velocity.VelocityEngineUtils;

@SpringBootApplication
public class VelocitySampleApplication implements CommandLineRunner{
	
	@Autowired
	private VelocityEngine velocityEngine;

	public static void main(String[] args) {
		SpringApplication.run(VelocitySampleApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		String vmTemplateName = "sample.vm";
		Order order = new Order();
		order.setName("Sample Order Name");
		order.setOrderId("qaz1234");
		order.setAmount(20000.00);
		
		//start items map
		Map<String, String> itemsMap = new HashMap<>();
		itemsMap.put("itemId1", "1");
		itemsMap.put("itemName1", "First Item Name");
		itemsMap.put("itemUrl1", "http://FirstImageUrl");
		itemsMap.put("itemId2", "2");
		itemsMap.put("itemName2", "Second Item Name");
		itemsMap.put("itemUrl2", "http://secondImageUrl");
			
		order.setItemsMap(itemsMap);
		order.setItemCount(2);
		
		//end items map
		
		
		
		Map<String, Object> model = new HashMap<>();
		model.put("mail", order);

		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, vmTemplateName, "UTF-8", model);
		System.out.println(text);
		
	}
}
