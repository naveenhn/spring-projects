/**
 * 
 */
package com.sarvah.mbg.domain.common.asset;

import javax.validation.constraints.NotNull;

/**
 * @author naveen
 *
 */
public class File {
	
	@NotNull (message = "File url cannot be null")
	protected String url;
	@NotNull (message = "File name cannot be null")
	protected String name;
	@NotNull (message = "File type cannot be null")
	protected FileType fileType;
	protected long size;
	
	public File() {
		// default constructor
	}
	
	public File(String name, String url) {
		this.name = name;
		this.url = url;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the fileType
	 */
	public FileType getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}
	
	
	

}
