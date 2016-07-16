/**
 * 
 */
package com.nav.digest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author naveen
 *
 */
public class Sha256Util {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String data = "084822fb-92f9-45ec-b0d9-17e98f047444:2016-02-03T12:00:00+01:00-1234567";
		String hash256Value = DigestUtils.sha256Hex(data);
		System.out.println(hash256Value);
		
		System.out.println(new String(Base64.getEncoder().encode(hash256Value.getBytes(StandardCharsets.UTF_8))));

	}

}
