/**
 * 
 */
package com.sarvah.mbg.domain.mongo.review;

/**
 * @author naveen
 *
 */
public class ConsolidatedRating {
	
	private Rating avgRating;
	private long oneRatingUserCount;
	private long twoRatingUserCount;
	private long threeRatingUserCount;
	private long fourRatingUserCount;
	private long fiveRatingUserCount;
	
	/**
	 * @return the oneRatingUserCount
	 */
	public long getOneRatingUserCount() {
		return oneRatingUserCount;
	}
	/**
	 * @param oneRatingUserCount the oneRatingUserCount to set
	 */
	public void setOneRatingUserCount(long oneRatingUserCount) {
		this.oneRatingUserCount = oneRatingUserCount;
	}
	/**
	 * @return the twoRatingUserCount
	 */
	public long getTwoRatingUserCount() {
		return twoRatingUserCount;
	}
	/**
	 * @param twoRatingUserCount the twoRatingUserCount to set
	 */
	public void setTwoRatingUserCount(long twoRatingUserCount) {
		this.twoRatingUserCount = twoRatingUserCount;
	}
	/**
	 * @return the threeRatingUserCount
	 */
	public long getThreeRatingUserCount() {
		return threeRatingUserCount;
	}
	/**
	 * @param threeRatingUserCount the threeRatingUserCount to set
	 */
	public void setThreeRatingUserCount(long threeRatingUserCount) {
		this.threeRatingUserCount = threeRatingUserCount;
	}
	/**
	 * @return the fourRatingUserCount
	 */
	public long getFourRatingUserCount() {
		return fourRatingUserCount;
	}
	/**
	 * @param fourRatingUserCount the fourRatingUserCount to set
	 */
	public void setFourRatingUserCount(long fourRatingUserCount) {
		this.fourRatingUserCount = fourRatingUserCount;
	}
	/**
	 * @return the fiveRatingUserCount
	 */
	public long getFiveRatingUserCount() {
		return fiveRatingUserCount;
	}
	/**
	 * @param fiveRatingUserCount the fiveRatingUserCount to set
	 */
	public void setFiveRatingUserCount(long fiveRatingUserCount) {
		this.fiveRatingUserCount = fiveRatingUserCount;
	}
	/**
	 * @return the avgRating
	 */
	public Rating getAvgRating() {
		return avgRating;
	}
	/**
	 * @param avgRating the avgRating to set
	 */
	public void setAvgRating(Rating avgRating) {
		this.avgRating = avgRating;
	}
	
	
	

}
