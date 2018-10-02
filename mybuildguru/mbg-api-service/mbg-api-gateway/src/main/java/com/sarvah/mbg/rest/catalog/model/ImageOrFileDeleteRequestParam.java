/**
 * 
 */
package com.sarvah.mbg.rest.catalog.model;

/**
 * @author shivu
 *
 */
public class ImageOrFileDeleteRequestParam {
	private String url;
	private String fileType;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
