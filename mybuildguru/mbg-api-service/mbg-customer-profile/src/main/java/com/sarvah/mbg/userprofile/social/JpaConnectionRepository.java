/**
 * 
 */
package com.sarvah.mbg.userprofile.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sarvah.mbg.domain.user.SocialUser;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.userprofile.dao.jpa.SocialUserRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaConnectionRepository.
 *
 * @author naveen
 */
public class JpaConnectionRepository implements ConnectionRepository{
	
	/** The user. */
	private final UserInfo user;
	
	/** The social user repository. */
	private final SocialUserRepository socialUserRepository;
	
	/** The connection factory locator. */
	private final ConnectionFactoryLocator connectionFactoryLocator;
	
	/** The text encryptor. */
	private final TextEncryptor textEncryptor;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JpaConnectionRepository.class);
	
	/** The connection mapper. */
	private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();
	
	
	/**
	 * Instantiates a new jpa connection repository.
	 *
	 * @param socialUserRepository the social user repository
	 * @param user the user
	 * @param connectionFactoryLocator the connection factory locator
	 * @param textEncryptor the text encryptor
	 */
	public JpaConnectionRepository(SocialUserRepository socialUserRepository, UserInfo user, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.socialUserRepository = socialUserRepository;
		this.user = user;		
		this.textEncryptor = textEncryptor;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#addConnection(org.springframework.social.connect.Connection)
	 */
	@Override
	@Transactional
	public void addConnection(Connection<?> connection) {
		logger.info("Adding a social user entry for the connection for user {}", user.getUsername());
		
		ConnectionData data = connection.createData();
		SocialUser socialUser = createSocialUser(user, data);
		socialUserRepository.save(socialUser);
		
		
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#findAllConnections()
	 */
	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		List<Connection<?>> resultList = connectionMapper.mapEntities(socialUserRepository.findAllByUser(user));
		
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#findConnections(java.lang.String)
	 */
	@Override
	public List<Connection<?>> findConnections(String providerId) {
		return connectionMapper.mapEntities(socialUserRepository.findByUserAndProviderId(user, providerId));
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#findConnections(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#findConnectionsToUsers(org.springframework.util.MultiValueMap)
	 */
	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
		if (providerUsers == null || providerUsers.isEmpty()) {
			throw new IllegalArgumentException("Unable to execute find:  providerUsers is not provided");
		}
		
		List<SocialUser> socialUsers = new ArrayList<>();
		
		for (String providerId : providerUsers.keySet()) {
			if(providerId != null && providerUsers.get(providerId) != null) {
				List<String> providerUserIds = providerUsers.get(providerId);
				List<SocialUser> sociaUsersForProvider = socialUserRepository.findByUserAndProviderIdAndProviderUserIdIn(user, providerId, providerUserIds);
				if(sociaUsersForProvider != null && !sociaUsersForProvider.isEmpty()){
					socialUsers.addAll(sociaUsersForProvider);
				}
				
			}
		}
		
		//seriously! I am coding this logic at 3:AM, I must be a Zombie or Insomniac!! 

		List<Connection<?>> resultList = connectionMapper.mapEntities(socialUsers);
		MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			List<String> userIds = providerUsers.get(providerId);
			List<Connection<?>> connections = connectionsForUsers.get(providerId);
			if (connections == null) {
				connections = new ArrayList<Connection<?>>(userIds.size());
				for (int i = 0; i < userIds.size(); i++) {
					connections.add(null);
				}
				connectionsForUsers.put(providerId, connections);
			}
			String providerUserId = connection.getKey().getProviderUserId();
			int connectionIndex = userIds.indexOf(providerUserId);
			connections.set(connectionIndex, connection);
		}
		return connectionsForUsers;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#findPrimaryConnection(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#getConnection(org.springframework.social.connect.ConnectionKey)
	 */
	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		try {
			return connectionMapper.mapEntity(socialUserRepository.findByUserAndProviderIdAndProviderUserId(user, connectionKey.getProviderId(), connectionKey.getProviderUserId()));
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchConnectionException(connectionKey);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#getConnection(java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#getPrimaryConnection(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
		
	}
	
	/**
	 * Gets the provider id.
	 *
	 * @param <A> the generic type
	 * @param apiType the api type
	 * @return the provider id
	 */
	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}
	
	/**
	 * Find primary connection.
	 *
	 * @param providerId the provider id
	 * @return the connection
	 */
	private Connection<?> findPrimaryConnection(String providerId) {
		List<Connection<?>> connections = connectionMapper.mapEntities(socialUserRepository.findByUserAndProviderId(user, providerId));
		if (connections.size() > 0) {
			return connections.get(0);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#removeConnection(org.springframework.social.connect.ConnectionKey)
	 */
	@Override
	@Transactional
	public void removeConnection(ConnectionKey connectionKey) {
		SocialUser socialUser  = socialUserRepository.findByUserAndProviderIdAndProviderUserId(user, connectionKey.getProviderId() , connectionKey.getProviderUserId());
		socialUserRepository.delete(socialUser);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#removeConnections(java.lang.String)
	 */
	@Override
	@Transactional
	public void removeConnections(String providerId) {
		List<SocialUser> socialUsers = socialUserRepository.findByUserAndProviderId(user, providerId);
		socialUserRepository.delete(socialUsers);
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionRepository#updateConnection(org.springframework.social.connect.Connection)
	 */
	@Override
	@Transactional
	public void updateConnection(Connection<?> connection) {
		ConnectionData connectionData = connection.createData();
		SocialUser socialUser = socialUserRepository.findByUserAndProviderIdAndProviderUserId(user, connectionData.getProviderId(), connectionData.getProviderUserId());
		
		if(socialUser != null) {
			
			socialUser.setDisplayName(connectionData.getDisplayName());
			socialUser.setProfileUrl(connectionData.getProfileUrl());
			socialUser.setImageUrl(connectionData.getImageUrl());
			socialUser.setAccessToken(encrypt(connectionData.getAccessToken()));
			socialUser.setSecret(encrypt(connectionData.getSecret()));
			socialUser.setRefreshToken(encrypt(connectionData.getRefreshToken()));
			socialUser.setExpirationDate(new Date(connectionData.getExpireTime()));

			socialUser = socialUserRepository.save(socialUser);
			
		}
	}
	
	
	/**
	 * Encrypt.
	 *
	 * @param text the text
	 * @return the string
	 */
	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}
	
	
	/**
	 * Creates the social user.
	 *
	 * @param user the user
	 * @param cdata the cdata
	 * @return the social user
	 */
	private SocialUser createSocialUser(UserInfo user, ConnectionData cdata){
		
		
		Long count  = socialUserRepository.countByUser(user);
		
		Integer rank = (int) (count != null ?  count + 1 : 0);
				
		SocialUser socialUser = new SocialUser();
		socialUser.setRank(rank);
		socialUser.setUser(user);
		socialUser.setDisplayName(cdata.getDisplayName());
		if(cdata.getExpireTime() != null) {
			socialUser.setExpirationDate(new Date(cdata.getExpireTime()));
		}
		
		
		socialUser.setImageUrl(cdata.getImageUrl());
		socialUser.setProfileUrl(cdata.getProfileUrl());
		socialUser.setProviderId(cdata.getProviderId());
		socialUser.setProviderName(cdata.getProviderId());
		socialUser.setProviderUserId(cdata.getProviderUserId());		
		socialUser.setAccessToken(encrypt(cdata.getAccessToken()));
		socialUser.setRefreshToken(encrypt(cdata.getRefreshToken()));
		socialUser.setSecret(encrypt(cdata.getSecret()));
		socialUser.setCreatedBy("SYSTEM");
		socialUser.setCreatedTime(new Date());
		socialUser.setLastModifiedTime(new Date());
		socialUser.setModifiedBy("SYSTEM");
		return socialUser;
	}
	
	
	
	
/**
 * The Class ServiceProviderConnectionMapper.
 */
private final class ServiceProviderConnectionMapper  {
		
		/**
		 * Map entities.
		 *
		 * @param socialUsers the social users
		 * @return the list
		 */
		public List<Connection<?>> mapEntities(List<SocialUser> socialUsers){
			List<Connection<?>> result = new ArrayList<Connection<?>>();
			for( SocialUser su: socialUsers){
				result.add(mapEntity(su));
			}
			return result;
		}
		
		/**
		 * Map entity.
		 *
		 * @param socialUser the social user
		 * @return the connection
		 */
		public Connection<?> mapEntity(SocialUser socialUser){
			ConnectionData connectionData = mapConnectionData(socialUser);
			ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
			return connectionFactory.createConnection(connectionData);
		}
		
		/**
		 * Map connection data.
		 *
		 * @param socialUser the social user
		 * @return the connection data
		 */
		private ConnectionData mapConnectionData(SocialUser socialUser){
			return new ConnectionData(
				socialUser.getProviderId(), 
				socialUser.getProviderUserId(), 
				socialUser.getDisplayName(), 
				socialUser.getProfileUrl(), 
				socialUser.getImageUrl(),
				decrypt(socialUser.getAccessToken()), 
				decrypt(socialUser.getSecret()),
				decrypt(socialUser.getRefreshToken()), 
				expireTime(socialUser.getExpirationDate().getTime())
			);
		}
		
		/**
		 * Decrypt.
		 *
		 * @param encryptedText the encrypted text
		 * @return the string
		 */
		private String decrypt(String encryptedText) {
			return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
		}
		
		/**
		 * Expire time.
		 *
		 * @param expireTime the expire time
		 * @return the long
		 */
		private Long expireTime(Long expireTime) {
			return expireTime == null || expireTime == 0 ? null : expireTime;
		}
		
	}



}
