/**
 * 
 */
package com.sarvah.mbg.userprofile.social;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.user.AuthRoles;
import com.sarvah.mbg.domain.user.SocialUser;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.userprofile.auth.exception.UserCreationException;
import com.sarvah.mbg.userprofile.dao.jpa.SocialUserRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaUsersConnectionRepository.
 *
 * @author naveen
 */
public class JpaUsersConnectionRepository implements UsersConnectionRepository{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JpaUsersConnectionRepository.class);
	
	
	/** The social user repository. */
	private final SocialUserRepository socialUserRepository;
	
	/** The connection factory locator. */
	private final ConnectionFactoryLocator connectionFactoryLocator;
	
	/** The text encryptor. */
	private final TextEncryptor textEncryptor;
	
	/** The user repository. */
	private final UserRepository userRepository;
	
	/** The user dao. */
	private final UserDAO userDAO;
	
	/** The role dao. */
	private final RoleDAO roleDAO;
	
	/** The password encoder. */
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Instantiates a new jpa users connection repository.
	 *
	 * @param repository the repository
	 * @param userRepository the user repository
	 * @param connectionFactoryLocator the connection factory locator
	 * @param textEncryptor the text encryptor
	 * @param userDAO the user dao
	 * @param roleDAO the role dao
	 * @param passwordEncoder the password encoder
	 */
	public JpaUsersConnectionRepository(final SocialUserRepository repository, final UserRepository userRepository,
			final ConnectionFactoryLocator connectionFactoryLocator, final TextEncryptor textEncryptor, final UserDAO userDAO, final RoleDAO roleDAO, final PasswordEncoder passwordEncoder
			) {
		this.socialUserRepository = repository;
		this.userRepository = userRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
		this.userDAO = userDAO;
		this.roleDAO = roleDAO;
		this.passwordEncoder = passwordEncoder;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.UsersConnectionRepository#createConnectionRepository(java.lang.String)
	 */
	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if(userId == null) {
			new IllegalArgumentException("UserId cannot be null");
		}
		
		UserInfo userInfo = userRepository.findOne(Integer.valueOf(userId));
		if(userInfo == null) {
			new IllegalArgumentException("User not found in our repository");
		}
		//instantiate a new connection repo for the user
		return new JpaConnectionRepository(socialUserRepository, userInfo, connectionFactoryLocator, textEncryptor);
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.UsersConnectionRepository#findUserIdsConnectedTo(java.lang.String, java.util.Set)
	 */
	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		List<String> providerUserIdList = new ArrayList<>(providerUserIds);
		List<SocialUser> socialUsers = socialUserRepository.findByProviderIdAndProviderUserIdIn(providerId, providerUserIdList);
		Set<String> usersIds = new HashSet<>();
		for (SocialUser socialUser : socialUsers) {
			if(socialUser != null && socialUser.getUser() != null) {
				String userId = Integer.toString(socialUser.getUser().getUserid());
				usersIds.add(userId);
				
			}
		}
		
		return usersIds;
	}
	
	
	/**
	 * Find user from social profile.
	 *
	 * @param connection the connection
	 * @return the user info
	 */
	private UserInfo findUserFromSocialProfile(Connection<?> connection) {
		
		UserProfile userProfile = connection.fetchUserProfile();
		
		if(userProfile != null && StringUtils.isNotBlank(userProfile.getEmail()) ) {
		UserInfo user =	userRepository.findByUsername(userProfile.getEmail());
		return user;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.UsersConnectionRepository#findUserIdsWithConnection(org.springframework.social.connect.Connection)
	 */
	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		List<String> userIds = new ArrayList<>();
		ConnectionKey connectionKey = connection.getKey();
		
		List<SocialUser> socialUsers = socialUserRepository.findByProviderIdAndProviderUserId(connectionKey.getProviderId(), connectionKey.getProviderUserId());
				
				if(socialUsers != null && !socialUsers.isEmpty()) {
					for (SocialUser socialUser : socialUsers) {
						String userId = Integer.toString(socialUser.getUser().getUserid());
						userIds.add(userId);
					}
					return userIds;
				}
				
				
				UserInfo user = findUserFromSocialProfile(connection);
				String userId = null;
				if(user == null) {
					//create a user, I really dint have anything to do in life !!
					
					try {
						userId = createNewUserDefault(connection);
						if(userId == null) throw new UserCreationException("Unable to create the default user");
					} catch (UserCreationException e) {
						//curse of social death, 
						logger.error("Unable to create default user for the social connection", e);
						//doing this at this hr because of some dumb person writing dumb emails!!!
					}
					
					
				}else {
					//user already present, found you bitch!
					userId = Integer.toString(user.getUserid());
					
				}
				
				//persist the Connection
		        createConnectionRepository(userId).addConnection(connection);
		        userIds.add(userId);

		        return userIds;
				
				
				
		
	}
	
	
	/**
	 * Create a default user in out system.
	 *
	 * @param connection the connection
	 * @return the string
	 * @throws UserCreationException the user creation exception
	 */
	private String createNewUserDefault(Connection<?> connection) throws UserCreationException  {
		
		UserProfile userProfile = connection.fetchUserProfile();
		
		if(userProfile != null && StringUtils.isNotBlank(userProfile.getEmail())) {
			//create a default user
			try {
				String username = userProfile.getEmail();
				// first create the user in mongo
				User user = userDAO.findByUsername(username);
				if (user == null) {
					user = new User();
				}
				user.setUsername(username);
				user.setFirstName(userProfile.getFirstName());
				user.setLastName(userProfile.getLastName());
				user.setFullName(userProfile.getFirstName() + " "
						+ userProfile.getLastName());
				user.setStatus(UserStatus.ACTIVE);
				user.setWebsiteUrl(connection.getProfileUrl());
				// add the default roles first depending on type
				com.sarvah.mbg.domain.mongo.userprofile.role.Role role = roleDAO
						.findByName(UserType.ENDUSER.toString().toUpperCase());
				Set<Role> roles = new HashSet<>();
				roles.add(role);
				user.setRoles(roles);
				
				// save to mongo
				user = userDAO.save(user);
				
				//save to mysql
				
				// mongoid to mysql, now save userInfo object to mysql which is
				// primarlily for auth only

				UserInfo userInfo = new UserInfo(username,
						passwordEncoder.encode(getTempPassword(10)),
						userProfile.getFirstName(),
						userProfile.getLastName(),
						AuthRoles.AUTHENTICATED, "SYSTEM");
				userInfo.setVerified(true);
				userInfo.setCreatedtime(new Date());
				userInfo.setLastmodifiedtime(new Date());
				// IMP: this is where there is link established between mongo &
				// SQL for any JustInCase reason
				userInfo.setMongoUserId(user.getId());
				// special case, hence enclose with try, just in case something
				// fails, revert mongo save
				
				// save
				userInfo = userRepository.save(userInfo);
				
				String userId = Integer.toString(userInfo.getUserid());
				return userId;

			}catch(Exception e) {
				//again social death of user even before being born!
				throw new UserCreationException("Unable to create a default user object for the social user", e);
			}
			
		}
			
		
		return null;
	}
	
	
	/** The Constant symbols. */
	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}

	/**
	 * Gets the temp password.
	 *
	 * @param length
	 *            the length
	 * @return the temp password
	 */
	private String getTempPassword(int length) {
		Random rand = new Random(9845936902l);
		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[rand.nextInt(symbols.length)];
		return new String(buf);
	}


}
