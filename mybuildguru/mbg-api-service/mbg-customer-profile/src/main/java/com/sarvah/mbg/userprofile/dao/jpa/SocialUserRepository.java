/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sarvah.mbg.domain.user.SocialUser;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author naveen
 *
 */
public interface SocialUserRepository extends JpaRepository<SocialUser, Integer>{
	
	List<SocialUser> findAllByUser(UserInfo user);
	List<SocialUser> findByUserAndProviderId(UserInfo user, String providerId);
	List<SocialUser> findByUserAndProviderUserId(UserInfo user, String providerUserId);
	List<SocialUser> findByUserAndProviderIdAndProviderUserIdIn(UserInfo user, String providerId, List<String> providerUserIds);
	List<SocialUser> findByProviderIdAndProviderUserId(String providerId, String providerUserId);
	List<SocialUser> findByProviderIdAndProviderUserIdIn(String providerId, List<String> providerUserIds);
	SocialUser findByUserAndProviderIdAndProviderUserId(UserInfo user, String providerId, String providerUserId);
	Long countByUser(UserInfo user);
   
}