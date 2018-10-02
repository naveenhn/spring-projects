package com.sarvah.mbg.userprofile.response;

public class TrackOrderStatusCount {
	private int placed;
	private int conformed;
	private int packed;
	private int dispatched;
	private int delivered;

	private int cancelled;
	private int unConformed;
	private int returned;
	private int count;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the cancelled
	 */
	public int getCancelled() {
		return cancelled;
	}

	/**
	 * @param cancelled
	 *            the cancelled to set
	 */
	public void setCancelled(int cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * @return the unConformed
	 */
	public int getUnConformed() {
		return unConformed;
	}

	/**
	 * @param unConformed
	 *            the unConformed to set
	 */
	public void setUnConformed(int unConformed) {
		this.unConformed = unConformed;
	}

	/**
	 * @return the returned
	 */
	public int getReturned() {
		return returned;
	}

	/**
	 * @param returned
	 *            the returned to set
	 */
	public void setReturned(int returned) {
		this.returned = returned;
	}

	/**
	 * @return the placed
	 */
	public int getPlaced() {
		return placed;
	}

	/**
	 * @param placed
	 *            the placed to set
	 */
	public void setPlaced(int placed) {
		this.placed = placed;
	}

	/**
	 * @return the conformed
	 */
	public int getConformed() {
		return conformed;
	}

	/**
	 * @param conformed
	 *            the conformed to set
	 */
	public void setConformed(int conformed) {
		this.conformed = conformed;
	}

	/**
	 * @return the packed
	 */
	public int getPacked() {
		return packed;
	}

	/**
	 * @param packed
	 *            the packed to set
	 */
	public void setPacked(int packed) {
		this.packed = packed;
	}

	/**
	 * @return the dispatched
	 */
	public int getDispatched() {
		return dispatched;
	}

	/**
	 * @param dispatched
	 *            the dispatched to set
	 */
	public void setDispatched(int dispatched) {
		this.dispatched = dispatched;
	}

	/**
	 * @return the delivered
	 */
	public int getDelivered() {
		return delivered;
	}

	/**
	 * @param delivered
	 *            the delivered to set
	 */
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}

}
