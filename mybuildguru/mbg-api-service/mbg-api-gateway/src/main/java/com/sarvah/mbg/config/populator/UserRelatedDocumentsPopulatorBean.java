/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.util.HashSet;
import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.userprofile.UserRelatedDocuments;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.userprofile.dao.mongo.UserRelatedDocumentsDAO;

/**
 * @author shivu s
 *
 */
public class UserRelatedDocumentsPopulatorBean {

	UserRelatedDocumentsDAO userRelatedDocumentsDAO;

	public UserRelatedDocumentsPopulatorBean(
			UserRelatedDocumentsDAO userRelatedDocumentsDAO) {
		this.userRelatedDocumentsDAO = userRelatedDocumentsDAO;
	}

	public void initUserRelatedDocuments() {
		userRelatedDocumentsDAO.deleteAll();
		// Related Document 1
//		UserRelatedDocuments userRelatedDocuments = new UserRelatedDocuments();
//
//		Set<UserType> UserTypes = new HashSet<>();
//		UserTypes.add(UserType.DEALER);
//		UserTypes.add(UserType.PROVIDER);
//		userRelatedDocuments.setForUserTypes(UserTypes);
//		File file = new File();
//		file.setUrl("https://mbgtest.blob.core.windows.net/files/users/Adminfileupload/J/JA/Architect_Registration.doc");
//		file.setName("Architect_Registration.doc");
//
//		userRelatedDocuments.setFile(file);
//
//		userRelatedDocuments.setActive(true);
//
//		userRelatedDocumentsDAO.insert(userRelatedDocuments);
//
//		// Related Document 2
//		UserRelatedDocuments userRelatedDocuments1 = new UserRelatedDocuments();
//
//		Set<UserType> UserTypes1 = new HashSet<>();
//		UserTypes1.add(UserType.SERVICEPROVIDER);
//		UserTypes1.add(UserType.SERVICEPROVIDER);
//		userRelatedDocuments1.setForUserTypes(UserTypes1);
//		File file1 = new File();
//		file1.setUrl("https://mbgtest.blob.core.windows.net/files/users/Adminfileupload/J/JA/Terms and conditions.doc");
//		file1.setName("Architect_Termsandconditions.doc");
//
//		userRelatedDocuments1.setFile(file1);
//
//		userRelatedDocuments1.setActive(false);
//
//		userRelatedDocumentsDAO.insert(userRelatedDocuments1);

	}

}
