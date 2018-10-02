/**
 * 
 */
package com.sarvah.mbg.rest.model;

/**
 * @author shivu
 *
 */
enum ASSSET_TYPE{
	VIDEO,
	IMAGE,
	SIMPLE
}

public class AssetResponse{
	
	
	private ASSSET_TYPE type;
	private String name;
	private String url;
	private String contentType;
	public ASSSET_TYPE getType() {
		return type;
	}
	public void setType(ASSSET_TYPE type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	} 
	

}
