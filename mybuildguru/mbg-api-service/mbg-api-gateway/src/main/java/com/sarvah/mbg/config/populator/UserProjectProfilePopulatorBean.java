/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.util.ArrayList;
import java.util.List;

import com.sarvah.mbg.domain.common.asset.FileType;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserProjectProfile;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectProfileDAO;

/**
 * @author Shiva
 *
 */
public class UserProjectProfilePopulatorBean {
	UserDAO userDAO;
	UserProjectProfileDAO userProjectProfileDAO;

	public UserProjectProfilePopulatorBean(UserDAO userDAO,
			UserProjectProfileDAO userProjectProfileDAO) {
		this.userDAO = userDAO;
		this.userProjectProfileDAO = userProjectProfileDAO;
	}

	public void initUserProjectProfile() {

		userProjectProfileDAO.deleteAll();

//		UserProjectProfile userProject1 = new UserProjectProfile();
//
//		userProject1.setProfileName("Villa Project");
//
//		User user = userDAO.findByUsername("admin@hindware.com");
//		userProject1.setUser(user);
//
//		List<Image> profileImages = new ArrayList<>();
//
//		Image img1 = new Image(
//				"login.png",
//				"https://mbgtest.blob.core.windows.net/images/user/userprojectprofile/V/VI/login.png",
//				624, 1360);
//
//		img1.setSize(39870);
//		img1.setFileType(FileType.IMAGE);
//
//		profileImages.add(img1);
//		userProject1.setProfileImages(profileImages);
//
//		userProjectProfileDAO.insert(userProject1);

	}

}
