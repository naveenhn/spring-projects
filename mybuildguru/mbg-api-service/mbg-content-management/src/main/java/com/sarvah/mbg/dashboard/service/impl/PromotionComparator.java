package com.sarvah.mbg.dashboard.service.impl;

import java.util.Comparator;
import java.util.Date;

import com.sarvah.mbg.domain.dashboard.Dashboard;

/**
 * 
 * @author Raju
 *
 */

public class PromotionComparator implements Comparator<Dashboard> {

	@Override
	public int compare(Dashboard s1, Dashboard s2) {
		int dashboardId1 = s1.getDashboardId();
		int dashboardId2 = s2.getDashboardId();

		if (dashboardId1 == dashboardId2)
			return 0;
		else if (dashboardId1 < dashboardId2)
			return 1;
		else
			return -1;

	}

}
