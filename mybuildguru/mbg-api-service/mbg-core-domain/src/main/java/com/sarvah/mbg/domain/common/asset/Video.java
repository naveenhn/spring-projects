/**
 * 
 */
package com.sarvah.mbg.domain.common.asset;


/**
 * @author naveen
 *
 */
public class Video extends com.sarvah.mbg.domain.common.asset.File {
	
	
	public Video() {
		// default constructor
	}
	/**
	 * Constructor
	 * @param name
	 * @param url
	 */
	public Video(String name, String url) {
		super(name, url);
		
	}
	
	
	public Video(String name, String url, Image img) {
		this(name, url);
		this.thumbnail = img;
	}
	
	private Image thumbnail;
	
	/**
	 * @return the thumbnail
	 */
	public Image getThumbnail() {
		return thumbnail;
	}
	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	

}
