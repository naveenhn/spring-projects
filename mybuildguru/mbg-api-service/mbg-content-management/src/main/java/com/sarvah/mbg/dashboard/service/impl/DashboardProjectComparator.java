package com.sarvah.mbg.dashboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.sarvah.mbg.domain.mongo.aceproject.Project;

public class DashboardProjectComparator implements Comparator<Project> {
	@Override
	public int compare(Project s1, Project s2) {

		java.util.Date date = new Date(s1.getCreatedDate().getTime());
		java.util.Date date1 = new Date(s2.getCreatedDate().getTime());
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
