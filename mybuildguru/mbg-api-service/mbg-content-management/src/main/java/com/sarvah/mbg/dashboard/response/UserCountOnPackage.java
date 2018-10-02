/**
 * 
 */
package com.sarvah.mbg.dashboard.response;

/**
 * @author shivu s
 *
 */
public class UserCountOnPackage {
	private String roleName;
	private long silver;
	private long gold;
	private long platinum;

	private long usersCount;
	private long noOfActiveUsers;
	private long noOfRegisterdUsers;
	private long noOfInactiveusers;

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the silver
	 */
	public long getSilver() {
		return silver;
	}

	/**
	 * @param silver
	 *            the silver to set
	 */
	public void setSilver(long silver) {
		this.silver = silver;
	}

	/**
	 * @return the gold
	 */
	public long getGold() {
		return gold;
	}

	/**
	 * @param gold
	 *            the gold to set
	 */
	public void setGold(long gold) {
		this.gold = gold;
	}

	/**
	 * @return the platinum
	 */
	public long getPlatinum() {
		return platinum;
	}

	/**
	 * @param platinum
	 *            the platinum to set
	 */
	public void setPlatinum(long platinum) {
		this.platinum = platinum;
	}

	/**
	 * @return the usersCount
	 */
	public long getUsersCount() {
		return usersCount;
	}

	/**
	 * @param usersCount
	 *            the usersCount to set
	 */
	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}

	/**
	 * @return the noOfActiveUsers
	 */
	public long getNoOfActiveUsers() {
		return noOfActiveUsers;
	}

	/**
	 * @param noOfActiveUsers
	 *            the noOfActiveUsers to set
	 */
	public void setNoOfActiveUsers(long noOfActiveUsers) {
		this.noOfActiveUsers = noOfActiveUsers;
	}

	/**
	 * @return the noOfRegisterdUsers
	 */
	public long getNoOfRegisterdUsers() {
		return noOfRegisterdUsers;
	}

	/**
	 * @param noOfRegisterdUsers
	 *            the noOfRegisterdUsers to set
	 */
	public void setNoOfRegisterdUsers(long noOfRegisterdUsers) {
		this.noOfRegisterdUsers = noOfRegisterdUsers;
	}

	/**
	 * @return the noOfInactiveusers
	 */
	public long getNoOfInactiveusers() {
		return noOfInactiveusers;
	}

	/**
	 * @param noOfInactiveusers
	 *            the noOfInactiveusers to set
	 */
	public void setNoOfInactiveusers(long noOfInactiveusers) {
		this.noOfInactiveusers = noOfInactiveusers;
	}
}
