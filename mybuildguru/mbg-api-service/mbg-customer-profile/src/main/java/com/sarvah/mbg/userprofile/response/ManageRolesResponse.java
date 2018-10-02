/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.List;

/**
 * @author shivu s
 *
 */
public class ManageRolesResponse {
	private List<ManageRolesView> roles;
	private int totalRoles;

	/**
	 * @return the roles
	 */
	public List<ManageRolesView> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<ManageRolesView> roles) {
		this.roles = roles;
	}

	/**
	 * @return the totalRoles
	 */
	public int getTotalRoles() {
		return totalRoles;
	}

	/**
	 * @param totalRoles
	 *            the totalRoles to set
	 */
	public void setTotalRoles(int totalRoles) {
		this.totalRoles = totalRoles;
	}
}
