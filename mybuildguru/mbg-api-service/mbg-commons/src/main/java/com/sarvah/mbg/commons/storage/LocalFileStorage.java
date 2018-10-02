/**
 * 
 */
package com.sarvah.mbg.commons.storage;

import java.io.InputStream;
import java.util.List;

/**
 * @author shivu
 *
 */
public class LocalFileStorage implements FileStorage {

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.storage.FileStorage#createContainer(java.lang.String)
	 */
	@Override
	public void createContainer(String containerName) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.storage.FileStorage#uploadFile(java.lang.String, java.lang.String, java.io.InputStream, long)
	 */
	@Override
	public String uploadFile(String containerName, String fileName,
			InputStream fileInputStream, long contentlength) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.storage.FileStorage#listFiles(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> listFiles(String containerName, String subDirectory)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.storage.FileStorage#deleteFile(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteFile(String containerName, String fileName)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
