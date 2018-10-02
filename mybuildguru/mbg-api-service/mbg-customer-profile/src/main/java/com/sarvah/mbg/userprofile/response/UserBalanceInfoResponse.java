/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author shivu s
 *
 */
public class UserBalanceInfoResponse {
	private Double invsetedBalance;
	private Double currentBalance;
	/**
	 * @return the invsetedBalance
	 */
	public Double getInvsetedBalance() {
		return invsetedBalance;
	}
	/**
	 * @param invsetedBalance the invsetedBalance to set
	 */
	public void setInvsetedBalance(Double invsetedBalance) {
		this.invsetedBalance = invsetedBalance;
	}
	/**
	 * @return the currentBalance
	 */
	public Double getCurrentBalance() {
		return currentBalance;
	}
	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
}
