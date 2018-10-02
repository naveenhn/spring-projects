package com.sarvah.mbg.dashboard.response;

/**
 * 
 * @author Raju
 *
 */

public class DashBoardStatusCount {

	private long inMbg;
	private long rejected;
	private long waitingApproval;

	private long saved;
	private long deactivated;

	public long getSaved() {
		return saved;
	}

	public void setSaved(long saved) {
		this.saved = saved;
	}

	public long getDeactivated() {
		return deactivated;
	}

	public void setDeactivated(long deactivated) {
		this.deactivated = deactivated;
	}

	public long getInMbg() {
		return inMbg;
	}

	public void setInMbg(long inMbg) {
		this.inMbg = inMbg;
	}

	public long getRejected() {
		return rejected;
	}

	public void setRejected(long rejected) {
		this.rejected = rejected;
	}

	public long getWaitingApproval() {
		return waitingApproval;
	}

	public void setWaitingApproval(long waitingApproval) {
		this.waitingApproval = waitingApproval;
	}

}
