/**
 * 
 */
package com.sarvah.mbg.rest.review.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author naveen
 * @author sumanth
 *
 */
public class ProductsCommentsResponse  extends AbstractCollectionResponse{
	private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
