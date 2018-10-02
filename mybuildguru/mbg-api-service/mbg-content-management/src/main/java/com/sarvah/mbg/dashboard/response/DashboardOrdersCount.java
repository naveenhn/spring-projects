package com.sarvah.mbg.dashboard.response;

/**
 * 
 * @author Raju
 *
 */

public class DashboardOrdersCount {
	private int placed;
	private int confirmed;
	private int packed;
	private int dispatched;
	private int delivered;

	private int cancelled;
	private int unConfirmed;
	private int returned;
	private int count;

	private int ItemsCount;

	/**
	 * @return the itemsCount
	 */
	public int getItemsCount() {
		return ItemsCount;
	}

	/**
	 * @param itemsCount
	 *            the itemsCount to set
	 */
	public void setItemsCount(int itemsCount) {
		ItemsCount = itemsCount;
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
	 * @return the confirmed
	 */
	public int getConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the unConfirmed
	 */
	public int getUnConfirmed() {
		return unConfirmed;
	}

	/**
	 * @param unConfirmed the unConfirmed to set
	 */
	public void setUnConfirmed(int unConfirmed) {
		this.unConfirmed = unConfirmed;
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

}
