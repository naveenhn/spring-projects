/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import java.util.Comparator;

import com.sarvah.mbg.batch.model.PriceInfo;

/**
 * @author shivu
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
}
