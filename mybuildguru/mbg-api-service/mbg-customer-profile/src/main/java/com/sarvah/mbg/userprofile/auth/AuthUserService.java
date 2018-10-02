/**
 * 
 */
package com.sarvah.mbg.userprofile.auth;

import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.domain.user.auth.VerificationToken;
import com.sarvah.mbg.userprofile.auth.exception.AuthenticationException;
import com.sarvah.mbg.userprofile.auth.exception.AuthorizationException;
import com.sarvah.mbg.userprofile.auth.exception.TokenAlreadyVerifiedException;
import com.sarvah.mbg.userprofile.auth.exception.TokenExpiredException;
import com.sarvah.mbg.userprofile.auth.exception.TokenNotFoundException;
import com.sarvah.mbg.userprofile.auth.exception.UserCreationException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.auth.model.AuthorizationRequest;
import com.sarvah.mbg.userprofile.auth.model.CreateUserBean;
import com.sarvah.mbg.userprofile.auth.model.RegisterUserBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface AuthUserService.
 *
 * @author naveen
 */
public interface AuthUserService {

	/**
	 * Register new user.
	 *
	 * @param registerUserBean
	 *            the register user bean
	 * @return the api user
	 * @throws UserCreationException
	 *             the user creation exception
	 */
	ApiUser registerNewUser(RegisterUserBean registerUserBean)
			throws UserCreationException;

	/**
	 * method for user login.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the api user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	ApiUser authenticate(String username, String password)
			throws AuthenticationException;

	/**
	 * Gets the user.
	 *
	 * @param username
	 *            the username
	 * @return the user
	 */
	ApiUser getUser(String username);

	/**
	 * Save user.
	 *
	 * @param username
	 *            the username
	 * @return the api user
	 */
	ApiUser saveUser(String username);

	/**
	 * method for Send verification token.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 * @throws Exception
	 *             the exception
	 */
	VerificationToken sendVerificationToken(String username)
			throws AuthenticationException, Exception;

	/**
	 * Send forgot password token.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 * @throws Exception
	 *             the exception
	 */
	VerificationToken sendForgotPasswordToken(String username)
			throws AuthenticationException, Exception;

	/**
	 * Send user otp.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 * @throws UserCommException
	 */
	VerificationToken sendUserOTP(String username)
			throws AuthenticationException, UserCommException;

	VerificationToken sendUsernameUpdateOTP(String username, String userId)
			throws AuthenticationException, UserCommException;

	/**
	 * Reset password.
	 *
	 * @param base64EncodedToken
	 *            the base64 encoded token
	 * @param password
	 *            the password
	 * @return the verification token
	 * @throws TokenAlreadyVerifiedException
	 *             the token already verified exception
	 * @throws TokenNotFoundException
	 *             the token not found exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 * @throws Exception
	 *             the exception
	 */
	VerificationToken resetPassword(String base64EncodedToken, String password)
			throws TokenAlreadyVerifiedException, TokenNotFoundException,
			TokenExpiredException, Exception;

	/**
	 * Reset password using otp.
	 *
	 * @param username
	 *            the username
	 * @param base64EncodedToken
	 *            the base64 encoded token
	 * @param password
	 *            the password
	 * @return the verification token
	 * @throws TokenNotFoundException
	 *             the token not found exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 * @throws TokenAlreadyVerifiedException
	 *             the token already verified exception
	 * @throws UserCommException
	 *             the user comm exception
	 */
	VerificationToken resetPasswordUsingOTP(String username,
			String base64EncodedToken, String password)
			throws TokenNotFoundException, TokenExpiredException,
			TokenAlreadyVerifiedException, UserCommException;

	VerificationToken updateUsernameUsingOTP(String base64EncodedToken,
			String username, String userId) throws TokenNotFoundException,
			TokenExpiredException, TokenAlreadyVerifiedException, Exception;

	/**
	 * Authorize.
	 *
	 * @param authorizationRequest
	 *            the authorization request
	 * @return the api user
	 * @throws AuthorizationException
	 */
	ApiUser authorize(AuthorizationRequest authorizationRequest)
			throws AuthorizationException;

	/**
	 * method for verifyToken.
	 *
	 * @param base64EncodedToken
	 *            the base64 encoded token
	 * @return the verification token
	 * @throws TokenAlreadyVerifiedException
	 *             the token already verified exception
	 * @throws TokenNotFoundException
	 *             the token not found exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 * @throws Exception
	 *             the exception
	 */
	VerificationToken verify(String base64EncodedToken, String username)
			throws TokenAlreadyVerifiedException, TokenNotFoundException,
			TokenExpiredException, Exception;

	/**
	 * Creates the new user.
	 *
	 * @param registerUserRequestParam
	 *            the register user request param
	 * @return the api user
	 * @throws UserCreationException
	 *             the user creation exception
	 */
	ApiUser createNewUser(CreateUserBean registerUserRequestParam)
			throws UserCreationException;

	/**
	 * method for social login.
	 * 
	 * @param provider
	 * @param accessToken
	 * @return
	 * @throws AuthenticationException
	 */

	ApiUser socialLogin(String provider, String accessToken)
			throws AuthenticationException;

}
