/**
 * 
 */
package com.sarvah.mbg.domain.mongo.review;

import javax.validation.constraints.DecimalMax;

import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 * you can further more use this to store any meta-data like smile to display or something, going forward
 */
public class Rating {
	@DecimalMax(value="5.0")
	private Double ratingVal;
	private Description desc;
	/**
	 * @return the ratingVal
	 */
	public Double getRatingVal() {
		return ratingVal;
	}
	/**
	 * @param ratingVal the ratingVal to set
	 */
	public void setRatingVal(Double ratingVal) {
		this.ratingVal = ratingVal;
	}
	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	
	

}
