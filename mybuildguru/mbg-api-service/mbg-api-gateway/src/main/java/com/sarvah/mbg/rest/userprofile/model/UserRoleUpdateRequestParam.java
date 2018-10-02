/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

/**
 * @author shivu s
 *
 */
public class UserRoleUpdateRequestParam {
	
	private String roleName;
	private String desc;
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
