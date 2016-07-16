/**
 * 
 */
package com.nav.azure.storage;

import java.io.InputStream;
import java.util.List;

/**
 * @author naveen
 *
 */
public interface FileStorage {

	void createContainer(String containerName) throws Exception;

	String uploadFile(String containerName, String fileName, InputStream fileInputStream, long contentlength)
			throws Exception;

	List<String> listFiles(String containerName, String subDirectory) throws Exception;

	boolean deleteFile(String containerName, String fileName) throws Exception;



}

