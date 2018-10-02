package com.sarvah.mbg.reviews.model;


public class CommentUpdateRequestParam {
	private String ratingVal;
	private String desc;

	private String slug;
	private String text;

	private String expert;

	private String commentType;

	/**
	 * @return the ratingVal
	 */
	public String getRatingVal() {
		return ratingVal;
	}

	/**
	 * @param ratingVal
	 *            the ratingVal to set
	 */
	public void setRatingVal(String ratingVal) {
		this.ratingVal = ratingVal;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @param slug
	 *            the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the expert
	 */
	public String getExpert() {
		return expert;
	}

	/**
	 * @param expert
	 *            the expert to set
	 */
	public void setExpert(String expert) {
		this.expert = expert;
	}

	/**
	 * @return the commentType
	 */
	public String getCommentType() {
		return commentType;
	}

	/**
	 * @param commentType
	 *            the commentType to set
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

}
