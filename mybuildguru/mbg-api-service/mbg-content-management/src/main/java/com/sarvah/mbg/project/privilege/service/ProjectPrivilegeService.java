/**
 * 
 */
package com.sarvah.mbg.project.privilege.service;

/**
 * @author Shiva
 *
 */
public interface ProjectPrivilegeService {

	/**
	 * 
	 * @param uid
	 * @param action
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	Boolean checkPrivilege(String uid, String action, String resource)
			throws Exception;

}
