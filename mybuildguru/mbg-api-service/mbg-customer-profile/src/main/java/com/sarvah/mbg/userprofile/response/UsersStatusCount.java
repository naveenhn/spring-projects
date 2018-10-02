package com.sarvah.mbg.userprofile.response;

public class UsersStatusCount {
	private int activeUsers;
	private int inActiveUSers;

	/**
	 * @return the activeUsers
	 */
	public int getActiveUsers() {
		return activeUsers;
	}

	/**
	 * @param activeUsers
	 *            the activeUsers to set
	 */
	public void setActiveUsers(int activeUsers) {
		this.activeUsers = activeUsers;
	}

	/**
	 * @return the inActiveUSers
	 */
	public int getInActiveUSers() {
		return inActiveUSers;
	}

	/**
	 * @param inActiveUSers
	 *            the inActiveUSers to set
	 */
	public void setInActiveUSers(int inActiveUSers) {
		this.inActiveUSers = inActiveUSers;
	}

}
