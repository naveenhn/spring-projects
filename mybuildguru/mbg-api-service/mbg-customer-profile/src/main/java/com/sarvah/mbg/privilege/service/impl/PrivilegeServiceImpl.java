/**
 * 
 */
package com.sarvah.mbg.privilege.service.impl;

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
import com.sarvah.mbg.privilege.dao.mongo.PrivilegePromotionDAO;
import com.sarvah.mbg.privilege.service.PrivilegeService;
import com.sarvah.mbg.product.dao.mongo.ProviderProductDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectProfileDAO;

/**
 * @author Shiva
 *
 */

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	private static final Logger logger = LoggerFactory
			.getLogger(PrivilegeServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PrivilegeDAO privilegeDAO;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private UserProjectDAO projectDAO;

	@Autowired
	private PrivilegePromotionDAO promotionDAO;

	@Autowired
	private ProviderProductDAO productDAO;

	@Autowired
	private UserProjectProfileDAO userProjectProfileDAO;

	/**
	 * method for check privilege.
	 * 
	 * @param uid
	 * @param action
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean checkPrivilege(String uid, String action, String resource)
			throws Exception {

		User user = userDAO.findOne(uid);
		String capsAction;
		String capsResource;
		Set<Role> roles = new HashSet<Role>();
		Boolean retValue = false;
		Long countOfProj = null;
		int countOfAddress = 0;
		Long countOfProduct = null;
		Long countOfPromtion = null;

		if (user != null) {
			if (StringUtils.isNotBlank(action)
					&& StringUtils.isNotBlank(resource)) {
				capsAction = action.trim().toUpperCase(); // ADD
				capsResource = resource.trim().toUpperCase(); // PRODUCT

				roles = user.getRoles();
				Iterator<Role> it = roles.iterator();
				while (it.hasNext()) {
					Role role = it.next();
					
					if (role.getName().equalsIgnoreCase("ENDUSER")) {
						retValue = true;
					}
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

					if (privileges != null && privileges.size() != 0) {

						for (Privilege privilege : privileges) {

							Long constraintCount = Long.parseLong(privilege
									.getConstraint().getValue());

							if (StringUtils.containsIgnoreCase(resource,
									"Project")) {

								countOfProj = (long) projectDAO
										.countByUserId(uid); // 4

								if (countOfProj < constraintCount) {
									retValue = true;
									logger.info("Can add more projects");
								}

							}
							if (StringUtils.containsIgnoreCase(resource,
									"Product")) {

								countOfProduct = (long) productDAO
										.countByBrand_Provider_Id(uid); // 4

								if (countOfProduct < constraintCount) {
									retValue = true;
									logger.info("Can add more products");
								}
							}

							if (StringUtils.containsIgnoreCase(resource,
									"Address")) {

								User users = userDAO.findById(uid);
								countOfAddress = users.getAddresses().size();

								if (countOfAddress < constraintCount) {
									retValue = true;
									logger.info("Can add more Addresses");

								}
							}
							if (StringUtils.containsIgnoreCase(resource,
									"Promotion")) {

								countOfPromtion = promotionDAO
										.countByDealer(uid);

								if (countOfPromtion < constraintCount) {
									retValue = true;
									logger.info("Can add more Promotions");

								}
							}
							if (StringUtils.containsIgnoreCase(resource,
									"PROFILE")) {

								countOfProj = userProjectProfileDAO
										.countByUser(uid); // 4

								if (countOfProj < constraintCount) {
									retValue = true;
									logger.info("Can add more projects");
								}

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
