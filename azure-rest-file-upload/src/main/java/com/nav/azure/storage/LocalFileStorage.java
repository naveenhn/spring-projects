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
public class LocalFileStorage implements FileStorage {

	@Override
	public void createContainer(String containerName) {


	}



	@Override
	public List<String> listFiles(String containerName, String directory) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean deleteFile(String containerName, String fileName) {
		// TODO Auto-generated method stub
		return false;

	}



	@Override
	public String uploadFile(String containerName, String fileName, InputStream fileInputStream, long fileLength) {
		// TODO Auto-generated method stub
		return null;
	}

}
