package com.sarvah.mbg.dashboard.service.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * 
 * @author Raju
 *
 */

public class ProductComparator implements Comparator<Product> {

	@Override
	public int compare(Product s1, Product s2) {

		/*
		 * String inputDate =String.valueOf(s1.getCreatedDate().getTime()) ;
		 * String inputDate1=String.valueOf(s2.getCreatedDate().getTime()) ;
		 * 
		 * String format = "yyyy-MM-dd'T'HH:mm:ssz";
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat(format); Date d = null;
		 * Date d1 = null; try { d = sdf.parse(inputDate);
		 * d1=sdf.parse(inputDate1); } catch (ParseException e) {
		 * e.printStackTrace(); } if(d.before(d1)) return -1; else
		 * if(d.after(d1)) return 1; else return 0;
		 */

		// Normal date formate
		/*
		 * long date = s1.getCreatedDate().getTime(); long date1 =
		 * s2.getCreatedDate().getTime();
		 * 
		 * if (date == date1) return 0; else if (date < date1) return 1; else
		 * return -1;
		 */

		java.util.Date date = new Date(s1.getCreatedDate().getTime());
		java.util.Date date1 = new Date(s2.getCreatedDate().getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format1 = formatter.format(date);
		String format2 = formatter.format(date1);
		// System.out.println(format1);
		// System.out.println(format2);
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
