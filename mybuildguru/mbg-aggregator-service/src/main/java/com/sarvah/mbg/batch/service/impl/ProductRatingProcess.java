package com.sarvah.mbg.batch.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.batch.dao.mongo.CommentDAO;
import com.sarvah.mbg.batch.dao.mongo.ProductDAO;
import com.sarvah.mbg.batch.dao.mongo.UserDAO;
import com.sarvah.mbg.batch.service.MBGCommandBase;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;
import com.sarvah.mbg.domain.mongo.review.Rating;

/**
 * @author Shiva
 *
 */
@Component("ProductRatingProcess")
public class ProductRatingProcess implements MBGCommandBase {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductRatingProcess.class);

	@Value("${name:Naveen}")
	private String name;

	@Override
	public void execute() {
		logger.info("Product Rating Service running ==== Rating calculation");
		try {
			calculateRating();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Autowired
	MongoOperations operations;

	/** The comment DAO **/
	@Autowired
	private CommentDAO commentDAO;

	/** The user DAO **/
	@Autowired
	private UserDAO userDAO;

	/** The product DAO **/
	@Autowired
	private ProductDAO productDAO;

	private void calculateRating() {

		List<Product> productList = productDAO.findAll();
		logger.info("-----Product Rating------");
		logger.info("Count of Products:  {} ", productList.size());

		for (Product product : productList) {
			ConsolidatedRating consolidatedRating = new ConsolidatedRating();

			/* Initializing discussionId(Setting productId as discussionId) */
			String discussionId = product.getId();

			double totalRating = 0;

			/* Getting all the comments for the given discussionId */
			List<Comment> commentList = commentDAO
					.findByDiscussionId(discussionId);
			/* Obtaining count, which would help in calculating the average */
			int count = commentList.size();
			if (count != 0) {

				for (Comment comment : commentList) {
					/* Getting Rating value from the comment */

					/* For consolidated Rating */
					Integer oneRatingCount = 0;
					Integer twoRatingCount = 0;
					Integer threeRatingCount = 0;
					Integer fourRatingCount = 0;
					Integer fiveRatingCount = 0;

					double ratingVal = comment.getRating().getRatingVal();

					if (ratingVal == 1.0) {
						oneRatingCount++;
						consolidatedRating
								.setOneRatingUserCount(oneRatingCount);
					}
					if (ratingVal == 2.0) {
						twoRatingCount++;
						consolidatedRating
								.setTwoRatingUserCount(twoRatingCount);
					}
					if (ratingVal == 3.0) {
						threeRatingCount++;
						consolidatedRating
								.setThreeRatingUserCount(threeRatingCount);
					}
					if (ratingVal == 4.0) {
						fourRatingCount++;
						consolidatedRating
								.setFourRatingUserCount(fourRatingCount);
					}
					if (ratingVal == 5.0) {
						fiveRatingCount++;
						consolidatedRating
								.setFiveRatingUserCount(fiveRatingCount);
					}

					totalRating = totalRating + ratingVal;

				}
				/* Calculating average of all the rating values */
				double avgProductRating = totalRating / count;

				if (avgProductRating != 0) {
					/* Setting rating to the product */
					Rating rating = new Rating();
					rating.setRatingVal(avgProductRating);
					consolidatedRating.setAvgRating(rating);
					product.setConsolidatedRating(consolidatedRating);
					// product.setRating(productRating);
				}
				logger.info("Product Name: {}", product.getName());
				logger.info("Product Rating: {}", product
						.getConsolidatedRating().getAvgRating().getRatingVal());

				logger.info("oneStarRatingCount: "
						+ product.getConsolidatedRating()
								.getOneRatingUserCount());
				logger.info("twoStarRatingCount: "
						+ product.getConsolidatedRating()
								.getTwoRatingUserCount());
				logger.info("threeStarRatingCount: "
						+ product.getConsolidatedRating()
								.getThreeRatingUserCount());
				logger.info("fourStarRatingCount: "
						+ product.getConsolidatedRating()
								.getFourRatingUserCount());
				logger.info("fiveStarFRatingCount: "
						+ product.getConsolidatedRating()
								.getFiveRatingUserCount());

				productDAO.save(product);
			}

		}
	}
}
