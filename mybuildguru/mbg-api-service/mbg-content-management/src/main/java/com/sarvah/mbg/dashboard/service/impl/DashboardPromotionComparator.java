package com.sarvah.mbg.dashboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.sarvah.mbg.domain.mongo.marketing.Promotion;

public class DashboardPromotionComparator implements Comparator<Promotion> {

	@Override
	public int compare(Promotion s1, Promotion s2) {

		java.util.Date date = new Date(s1.getStartDate().getTime());
		java.util.Date date1 = new Date(s2.getStartDate().getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format1 = formatter.format(date);
		String format2 = formatter.format(date1);
		if (format2 == format1) {
			return 0;
		}
		if (format1 == null) {
			return -1;
		}
		if (format2 == null) {
			return 1;
		}
		return format2.compareTo(format1);
	}

}
