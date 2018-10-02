/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author Shivu
 *
 */
public class QuoteCountResponse {
	private int created;
	private int confirmed;
	private int onHold;
	private int rejected;

	/**
	 * @return the created
	 */
	public int getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(int created) {
		this.created = created;
	}

	/**
	 * @return the confirmed
	 */
	public int getConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed
	 *            the confirmed to set
	 */
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the onHold
	 */
	public int getOnHold() {
		return onHold;
	}

	/**
	 * @param onHold
	 *            the onHold to set
	 */
	public void setOnHold(int onHold) {
		this.onHold = onHold;
	}

	/**
	 * @return the rejected
	 */
	public int getRejected() {
		return rejected;
	}

	/**
	 * @param rejected
	 *            the rejected to set
	 */
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
}
