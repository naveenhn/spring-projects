/**
 * 
 *//*
package com.sarvah.mbg.user.dao;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;

*//**
 * @author naveen
 * 
 *         Testing code only, never reference this code for real implementations
 *
 *//*
@Component
public class UserInfoService {
	@Autowired
	private UserRepository userInfoDao;
	private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

	@PostConstruct
	public void init() {

		try {

			UserInfo systemUserInfo = userInfoDao.findByUsername("system");
			if (systemUserInfo == null) {

				// userInfoRepository.delete(systemUserInfo);

				// create a user info
				UserInfo userInfo = new UserInfo("system", "naveen007", "SYSTEM", "SYSTEM", "admin", null);
				userInfo.setCreatedby("naveen");
				userInfo.setModifiedby("naveen");
				userInfo.setCreatedtime(new Date());
				userInfo.setLastmodifiedtime(new Date());

				userInfoDao.save(userInfo);

				systemUserInfo = userInfoDao.findByUsername("system");
				assert (systemUserInfo != null);
				log.info("" + systemUserInfo);
			}

		} catch (Exception e) {
			log.error("Error occured trying to create a ", e);
		}

	}

}*/
