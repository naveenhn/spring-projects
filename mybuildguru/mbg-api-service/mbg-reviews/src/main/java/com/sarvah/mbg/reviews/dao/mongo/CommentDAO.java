package com.sarvah.mbg.reviews.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.review.Comment;

public interface CommentDAO extends MongoRepository<Comment, String>{
/*
/v1/catalog/products/{pid}/comments				GET			
/v1/catalog/products/{pid}/comments/count		GET			
/v1/catalog/products/{pid}/comments				POST			
/v1/catalog/products/{pid}/comments/{cmtid}		PUT
/v1/catalog/products/{pid}/comments/{cmtid}		DELETE
 */
	List<Comment> findByDiscussionId(String discussionId);
	Page<Comment> findByDiscussionId(String discussionId, Pageable pageable);
	int countByDiscussionId(String discussionId);		
}
