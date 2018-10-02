/**
 * 
 */
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
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;
import com.sarvah.mbg.domain.mongo.review.Rating;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
@Component("UserRatingProcess")
public class UserRatingProcess implements MBGCommandBase {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRatingProcess.class);

	@Value("${name:Naveen}")
	private String name;

	@Override
	public void execute() {
		logger.info("User Rating Service running ==== Rating calculation");
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

		List<User> userList = userDAO.findAll();
		logger.info("-----User Rating------");
		logger.info("Count of users: " + userList.size());

		for (User user : userList) {

			ConsolidatedRating consolidatedRating = new ConsolidatedRating();

			/* Initializing discussionId(Setting userId as discussionId) */
			String discussionId = user.getId();
			double totalRating = 0;

			/* Getting all the comments for the given discussionId */
			List<Comment> commentList = commentDAO
					.findByDiscussionId(discussionId);

			/* Obtaining count, which would help in calculating the average */
			int count = commentList.size();
			if (count != 0) {

				for (Comment comment : commentList) {
					/* Getting Rating value from the comment */

					/* For Consolidated rating */
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
				// double userRating = totalRating / count;

				double avgUserRating = totalRating / count;
				if (avgUserRating != 0) {
					/* Setting rating to the product */
					Rating rating = new Rating();
					rating.setRatingVal(avgUserRating);
					consolidatedRating.setAvgRating(rating);
					user.setRating(consolidatedRating);
				}
				logger.info("User Name: {}", user.getFullName());
				logger.info("User Rating: {}", user.getRating().getAvgRating()
						.getRatingVal());

				logger.info("oneStarRatingCount: "
						+ user.getRating().getOneRatingUserCount());
				logger.info("twoStarRatingCount: "
						+ user.getRating().getTwoRatingUserCount());
				logger.info("threeStarRatingCount: "
						+ user.getRating().getThreeRatingUserCount());
				logger.info("fourStarRatingCount: "
						+ user.getRating().getFourRatingUserCount());
				logger.info("fiveStarRatingCounSt: "
						+ user.getRating().getFiveRatingUserCount());

				userDAO.save(user);
			}
		}
	}

}
