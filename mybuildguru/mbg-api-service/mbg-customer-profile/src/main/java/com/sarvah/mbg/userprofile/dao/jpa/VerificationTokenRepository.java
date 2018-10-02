/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.domain.user.auth.VerificationToken;
import com.sarvah.mbg.domain.user.auth.VerificationTokenType;

/**
 * @author naveen
 *
 */
public interface VerificationTokenRepository extends
		CrudRepository<VerificationToken, Integer> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser_UsernameAndTokenAndTokenType(String username,
			String token, VerificationTokenType tokenType);

	VerificationToken findByTokenAndTokenType(String decodedToken,
			VerificationTokenType otp);

	VerificationToken findByTokenAndTokenTypeAndUser(String decodedToken,
			VerificationTokenType otp, UserInfo user);

	VerificationToken findByUser_PhonenumberAndTokenAndTokenType(Long username,
			String decodedToken, VerificationTokenType otp);

}
