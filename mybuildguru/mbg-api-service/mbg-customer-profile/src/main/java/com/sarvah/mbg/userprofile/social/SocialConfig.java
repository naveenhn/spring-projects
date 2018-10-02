/**
 * 
 */
package com.sarvah.mbg.userprofile.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

import com.sarvah.mbg.userprofile.dao.jpa.SocialUserRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author naveen
 *
 */
@Configuration
@EnableSocial  // very important, otherwise google social connectionfactory is not added. In general the connectionFactoryLocator is not created properly
public class SocialConfig {
	
	
	@Value("${spring.social.facebook.app-id}")
	private String facebook_appid;
	@Value("${spring.social.facebook.app-secret}")
	private String facebook_appsecret;
	
	@Value("${spring.social.google.app-id}")
	private String google_appid;
	
	@Value("${spring.social.google.app-id}")
	private String google_appsecret;
	
	
	
	@Autowired
	SocialUserRepository socialUserRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
			
	@Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
	
	
	
	
	@Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(facebook_appid, facebook_appsecret));
        registry.addConnectionFactory(new GoogleConnectionFactory(google_appid, google_appsecret));
        return registry;
    }
	
	@Bean(name="jpaUserConnectionRepository")
	public UsersConnectionRepository usersConnectionRepository() {
		
		JpaUsersConnectionRepository jpaUsersConnectionRepository = new JpaUsersConnectionRepository(socialUserRepository, userRepository, connectionFactoryLocator(), textEncryptor(), userDAO, roleDAO, passwordEncoder);
		return jpaUsersConnectionRepository;
	}
	

}
