package com.sarvah.mbg.dashboard.response;
/**
 * 
 * @author Raju
 *
 */

public class DashboardMyordersCount {

	private long processing;
	private long packed;
	private long delivered;
	private long orderscount;

	public long getOrderscount() {
		return orderscount;
	}

	public void setOrderscount(long orderscount) {
		this.orderscount = orderscount;
	}

	public long getProcessing() {
		return processing;
	}

	public void setProcessing(long processing) {
		this.processing = processing;
	}

	public long getPacked() {
		return packed;
	}

	public void setPacked(long packed) {
		this.packed = packed;
	}

	public long getDelivered() {
		return delivered;
	}

	public void setDelivered(long delivered) {
		this.delivered = delivered;
	}
}
