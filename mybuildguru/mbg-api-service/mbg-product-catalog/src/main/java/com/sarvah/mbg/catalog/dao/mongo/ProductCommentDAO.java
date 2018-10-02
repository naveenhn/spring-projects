package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.review.Comment;

public interface ProductCommentDAO extends MongoRepository<Comment, String>{
	
	List<Comment> findByRating_ratingValBetween(Double from,Double to);

	List<Comment> findByDiscussionId(String dealerId);

	Long countByDiscussionId(String dealerId);

}
