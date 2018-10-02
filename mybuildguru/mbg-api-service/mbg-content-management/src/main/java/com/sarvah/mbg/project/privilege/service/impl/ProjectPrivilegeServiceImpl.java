/**
 * 
 */
package com.sarvah.mbg.project.privilege.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.project.dao.mongo.BidsDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectPrivilegeDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectUserDAO;
import com.sarvah.mbg.project.privilege.service.ProjectPrivilegeService;

/**
 * @author Shiva
 *
 */

@Service
public class ProjectPrivilegeServiceImpl implements ProjectPrivilegeService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProjectPrivilegeServiceImpl.class);

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	ProjectUserDAO userDAO;

	@Autowired
	ProjectPrivilegeDAO privilegeDAO;

	@Autowired
	BidsDAO bidDAO;

	/**
	 * @throws Exception
	 * 
	 */
	@Override
	public Boolean checkPrivilege(String uid, String action, String resource)
			throws Exception {
		// User check
		// NUll check
		// Trim and Capitalise
		// Get Package name for user: silver/10 @PathParam
		// Query: product count by using user info/5
		// compare count of product N constraints and pass true/false

		User user = userDAO.findOne(uid);
		String capsAction;
		String capsResource;
		Set<Role> roles = new HashSet<Role>();
		Boolean retValue = false;
		Long countOfProj = null;

		if (user != null) {
			if (StringUtils.isNotBlank(action)
					&& StringUtils.isNotBlank(resource)) {
				capsAction = action.trim().toUpperCase(); // ADD
				capsResource = resource.trim().toUpperCase(); // PRODUCT

				roles = user.getRoles();
				Iterator<Role> it = roles.iterator();
				while (it.hasNext()) {
					Role role = it.next();
					UserPackage userPackage = role.getUserPackage(); // SILVER

					String queryString = userPackage.getName()
							+ " PRIVILEGE TO " + capsAction + " "
							+ capsResource; // SILVER
											// PRIVILEGE
											// TO
											// ADD
											// PRODUCT

					Set<Privilege> privileges = new HashSet<Privilege>();

					Privilege getPrivilege = privilegeDAO
							.findByName(queryString);

					if (getPrivilege != null) {
						privileges.add(getPrivilege);
					}

					for (Privilege privilege : privileges) {

						Long constraintCount = Long.parseLong(privilege
								.getConstraint().getValue());

						if (StringUtils.containsIgnoreCase(resource, "Project")) {

							countOfProj = (long) bidDAO.countByUser_Id(uid); // 4

							if (countOfProj < constraintCount) {
								retValue = true;
								logger.info("Can bid more projects");
							} else {
								logger.info("Cannot bid for more Projects, upgrade your project");
							}

						}

					}
				}

			}
		} else {
			logger.info("User doesn't exist!!");
			throw new Exception("User doesn't exist");
		}

		return retValue;
	}
}
