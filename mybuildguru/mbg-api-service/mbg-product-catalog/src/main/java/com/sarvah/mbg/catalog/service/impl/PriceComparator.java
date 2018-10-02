/**
 * 
 */
package com.sarvah.mbg.catalog.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Shiva
 *
 */
public class PriceComparator implements Comparator<PriceInfo> {

	@Override
	public int compare(PriceInfo o1, PriceInfo o2) {
		double cost1 = 0;
		double cost2 = 0;
		cost1 = o1.getCost();
		cost2 = o2.getCost();

		if (cost1 == cost2)
			return 0;
		if (cost1 > cost2)
			return 1;
		else
			return -1;
		

	}
	
	
//simple test
	public static void main(String[] args) {
		//sample sorting 
		
		List<PriceInfo> priceInfos = new ArrayList<>();
		
		PriceInfo p1 = new PriceInfo();
		p1.setCost(200.00);
		p1.setDealerId("Naveen-123");
		p1.setShippingType("Local");
		
		priceInfos.add(p1);
		
		PriceInfo p2 = new PriceInfo();
		p2.setCost(400.00);
		p2.setDealerId("Naveen-456");
		p2.setShippingType("Local");
		
		priceInfos.add(p2);
		
		PriceInfo p3 = new PriceInfo();
		p3.setCost(600.00);
		p3.setDealerId("Naveen-789");
		p3.setShippingType("Zonal");
		
		priceInfos.add(p3);
		PriceInfo p4 = new PriceInfo();
		p4.setCost(100.00);
		p4.setDealerId("Naveen-111");
		p4.setShippingType("Local");
		
		priceInfos.add(p4);
		PriceInfo p5 = new PriceInfo();
		p5.setCost(800.00);
		p5.setDealerId("Naveen-222");
		p5.setShippingType("National");
		
		priceInfos.add(p5);
		
		
		Collections.sort(priceInfos, new PriceComparator());
		
		for (PriceInfo priceInfo : priceInfos) {
			System.out.println(priceInfo.getCost());
		}
		
		
		
		
		
	}
}