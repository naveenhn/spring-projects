/**
 * 
 */
package com.sarvah.mbg.commons.storage;

import java.io.InputStream;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface FileStorage.
 *
 * @author naveen
 */
public interface FileStorage {

	/**
	 * Creates the container.
	 *
	 * @param containerName the container name
	 * @throws Exception the exception
	 */
	void createContainer(String containerName) throws Exception;

	/**
	 * Upload file.
	 *
	 * @param containerName the container name
	 * @param fileName the file name
	 * @param fileInputStream the file input stream
	 * @param contentlength the contentlength
	 * @return the string
	 * @throws Exception the exception
	 */
	String uploadFile(String containerName, String fileName, InputStream fileInputStream, long contentlength)
			throws Exception;

	/**
	 * List files.
	 *
	 * @param containerName the container name
	 * @param subDirectory the sub directory
	 * @return the list
	 * @throws Exception the exception
	 */
	List<String> listFiles(String containerName, String subDirectory) throws Exception;

	/**
	 * Delete file.
	 *
	 * @param containerName the container name
	 * @param fileName the file name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean deleteFile(String containerName, String fileName) throws Exception;



}

