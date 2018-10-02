/**
 * 
 */
package com.sarvah.mbg.privilege.service;

/**
 * @author Shiva
 *
 */
public interface PrivilegeService {
	
	/**
	 * method for check privilege.
	 * @param uid
	 * @param action
	 * @param resource
	 * @return
	 * @throws Exception 
	 */
	Boolean checkPrivilege(String uid, String action, String resource) throws Exception;

}
