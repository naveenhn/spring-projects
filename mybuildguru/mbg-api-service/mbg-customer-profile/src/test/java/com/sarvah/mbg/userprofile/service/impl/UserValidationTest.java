///**
// * 
// */
//package com.sarvah.mbg.userprofile.service.impl;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import com.sarvah.mbg.domain.mongo.userprofile.User;
//import com.sarvah.mbg.userprofile.exception.UserInputValidationException;
//
///**
// * @author shivu s
// *
// */
//public class UserValidationTest {
//
//	UserValidation userValidation = new UserValidation();
//
//
//
//	/**
//	 * Test method for
//	 * {@link com.sarvah.mbg.userprofile.service.impl.UserValidation#validatePhoneNumber(java.lang.String)}
//	 * .
//	 * @throws UserInputValidationException 
//	 */
//	@Test
//	public void testValidatePhoneNumber() throws UserInputValidationException {
//		assertTrue(userValidation.validatePhoneNumber("9611652281"));
//		assertTrue(userValidation.validatePhoneNumber("-9611652281"));
//		assertTrue(userValidation.validatePhoneNumber("91-9611652281"));
//		assertTrue(userValidation.validatePhoneNumber("+91-9611652281"));
//		assertTrue(userValidation.validatePhoneNumber("+9611652281"));
//		assertTrue(userValidation.validatePhoneNumber("919611652281"));
//		
//		
//		try {
//			userValidation.validatePhoneNumber("961165228");
//			userValidation.validatePhoneNumber("96116522");
//			userValidation.validatePhoneNumber("9611652");
//			userValidation.validatePhoneNumber("@9611652281");
//		} catch (Exception e) {
//			assertTrue(e instanceof UserInputValidationException);
//		}
//	}
//
//	/**
//	 * Test method for
//	 * {@link com.sarvah.mbg.userprofile.service.impl.UserValidation#validateEmail(java.lang.String)}
//	 * .
//	 * @throws UserInputValidationException 
//	 */
//	@Test
//	public void testValidateEmail() throws UserInputValidationException {
//		assertTrue(userValidation.validateEmail("shivus@gmail.com"));
//		assertTrue(userValidation.validateEmail("shivus123@gmail.com"));
//		assertTrue(userValidation.validateEmail("shivus@yahoo.com"));
//		assertTrue(userValidation.validateEmail("shivus@gmail.in"));
//
//		try {
//			userValidation.validateEmail("shivus@.com");
//			userValidation.validateEmail("shivusgmail.com");
//			userValidation.validateEmail("shivus@gmail.");
//			userValidation.validateEmail("shivus@gmailcom");
//			userValidation.validateEmail("@gmail.com");
//		} catch (Exception e) {
//			assertTrue(e instanceof UserInputValidationException);
//		}
//	}
//	
//	/**
//	 * Test method for
//	 * {@link com.sarvah.mbg.userprofile.service.impl.UserValidation#userValidation(com.sarvah.mbg.domain.mongo.userprofile.User)}
//	 * .
//	 * @throws UserInputValidationException 
//	 */
//	@Test
//	public void testUserValidation() throws UserInputValidationException {
//		User user=new User();
//		user.setFirstName("shivu");
//		user.setLastName("S");
//		user.setUsername("shivu@gmail.com");
//		user.setSex("male");
//		assertTrue(userValidation.userValidation(user));
//		
//		User user1=new User();
//		user1.setFirstName("shivu");
//		user1.setLastName("S");
//		user1.setUsername("shivugmail.com");
//		user1.setSex("male");
//		
//		
//		User user2=new User();
//		user2.setFirstName("shivu");
//		user2.setUsername("shivugmail.com");
//		
//		
//		
//		try {
//			userValidation.userValidation(user1);
//			
//			userValidation.userValidation(user2);
//		} catch (Exception e) {
//			assertTrue(e instanceof UserInputValidationException);
//		}
//	} 
//}
