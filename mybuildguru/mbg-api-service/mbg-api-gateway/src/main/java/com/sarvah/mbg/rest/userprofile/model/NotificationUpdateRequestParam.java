package com.sarvah.mbg.rest.userprofile.model;

/**
 * @author Shiva
 *
 */
public class NotificationUpdateRequestParam {

	private String type;
	private String desc;
	private String text;
	private String archieved;
	private String acknowledged;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the archieved
	 */
	public String getArchieved() {
		return archieved;
	}
	/**
	 * @param archieved the archieved to set
	 */
	public void setArchieved(String archieved) {
		this.archieved = archieved;
	}
	/**
	 * @return the acknowledged
	 */
	public String getAcknowledged() {
		return acknowledged;
	}
	/**
	 * @param acknowledged the acknowledged to set
	 */
	public void setAcknowledged(String acknowledged) {
		this.acknowledged = acknowledged;
	}

	
}
