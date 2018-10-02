/**
 * 
 */
package com.sarvah.mbg.reviews.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.review.Author;
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.review.CommentType;
import com.sarvah.mbg.domain.mongo.review.Rating;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.reviews.dao.jpa.ReviewUserOrderDetailRepository;
import com.sarvah.mbg.reviews.dao.jpa.ReviewUserOrderRepository;
import com.sarvah.mbg.reviews.dao.mongo.CommentDAO;
import com.sarvah.mbg.reviews.dao.mongo.CommentProductDAO;
import com.sarvah.mbg.reviews.dao.mongo.CommentUserDAO;
import com.sarvah.mbg.reviews.service.ReviewService;

/**
 * @author sumanth
 *
 */
@Service
public class ReviewServiceImpl implements ReviewService {
	private static final Logger logger = LoggerFactory
			.getLogger(ReviewServiceImpl.class);
	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private CommentProductDAO commentProductDAO;

	@Autowired
	private CommentUserDAO commentUserDAO;

	@Autowired
	private ReviewUserOrderRepository userOrderDAO;

	@Autowired
	private ReviewUserOrderDetailRepository userOrderDetailsDAO;

	@Override
	public List<Comment> search(String discussionId, int page, int size,
			Sort sort) throws Exception {
		List<Comment> comment = new ArrayList<Comment>();
		if (StringUtils.isNotBlank(discussionId)) {
			Product product = commentProductDAO.findOne(discussionId);
			if (product != null) {
				logger.info("Discussion id is product id");
				comment = commentDAO.findByDiscussionId(discussionId,
						new PageRequest(page, size, sort)).getContent();
			} else {
				logger.info("Discussion id is not a product id");
				User user = commentUserDAO.findOne(discussionId);
				if (user != null) {
					logger.info("Discussion id is user id");
					comment = commentDAO.findByDiscussionId(discussionId,
							new PageRequest(page, size, sort)).getContent();
				} else {
					logger.info("User doesn't exists");
					throw new Exception("User doesn't exists");
				}
			}
		} else {
			comment = null;
			logger.info("Discussion Id can't be null");
			throw new Exception("Discussion Id can't be null");
		}
		return comment;
	}

	@Override
	public int count(String discussionId) throws Exception {
		int commentcount = 0;
		if (StringUtils.isNotBlank(discussionId)) {
			Product product = commentProductDAO.findOne(discussionId);
			if (product != null) {
				logger.info("Discussion id is product id");
				commentcount = commentDAO.countByDiscussionId(discussionId);
			} else {
				User users = commentUserDAO.findOne(discussionId);
				if (users != null) {
					logger.info("Discussion id is user id");
					commentcount = commentDAO.countByDiscussionId(discussionId);
				} else {
					logger.info("discussionId doesn't exists");
					throw new Exception("discussionId doesn't exists");
				}
			}
		} else {
			logger.info("Discussion Id can't be null");
			throw new Exception("Discussion Id can't be null");
		}
		return commentcount;
	}

	/**
	 * Method to create new comment
	 * 
	 * @param ratingval
	 * @param desc
	 * @param slug
	 * @param text
	 * @param expert
	 * @param discussionId
	 * @return
	 * @throws Exception
	 */
	public Comment createComment(String userId, String ratingval, String desc,
			String slug, String text, String expert, String discussionId)
			throws Exception {
		Comment comment = new Comment();

		User user = commentUserDAO.findOne(userId);

		if (StringUtils.isNotBlank(discussionId)) {

			Product product = commentProductDAO.findOne(discussionId);
			if (product != null) {
				logger.info("Discussion id is product id");

				// checking whether the logged in user already bought the
				// product or not.If the user bought the product and the product
				// status is in delivered state,the value of check is true.
				boolean check = verifyUserBoughtProduct(user, discussionId);
				if (check == true) {
					if (ratingval != null) {
						Rating rating = new Rating();
						rating.setRatingVal(Double.parseDouble(ratingval));
						if (desc != null) {
							Description descr = new Description();
							descr.setLang("Eng");
							descr.setVal(desc);
							rating.setDesc(descr);
						}
						comment.setRating(rating);
					}

					comment.setDiscussionId(discussionId);

					if (StringUtils.isNotBlank(slug)) {
						comment.setSlug(slug);
					}

					comment.setCommentType(CommentType.PRODUCT);

					if (text != null) {
						comment.setText(text);
					}
					if (user != null) {
						Author author = new Author();
						author.setUserId(user.getId());
						author.setName(user.getFullName());
						if (StringUtils.contains(expert, "true")) {
							author.setExpert(true);
						} else {
							author.setExpert(false);
						}
						comment.setAuthor(author);
					}

					commentDAO.insert(comment);
				} else {
					logger.info("user has not bought this product");
					throw new Exception("user has not bought this product");
				}
			} else {
				User users = commentUserDAO.findOne(discussionId);
				if (users != null) {
					if (ratingval != null && desc != null) {
						Rating rating = new Rating();
						rating.setRatingVal(Double.parseDouble(ratingval));
						Description descr = new Description();
						descr.setLang("Eng");
						descr.setVal(desc);
						rating.setDesc(descr);
						comment.setRating(rating);
					}

					comment.setDiscussionId(discussionId);

					if (StringUtils.isNotBlank(slug)) {
						comment.setSlug(slug);
					}

					comment.setCommentType(CommentType.USER);

					if (text != null) {
						comment.setText(text);
					}

					// User user = commentUserDAO.findOne("1");
					if (user != null) {
						Author author = new Author();
						author.setUserId(user.getId());
						author.setName(user.getFullName());
						if (StringUtils.contains(expert, "true")) {
							author.setExpert(true);
						} else {
							author.setExpert(false);
						}
						comment.setAuthor(author);
					}
					commentDAO.insert(comment);
				}
			}

		} else {
			comment = null;
			logger.info("Discussion Id is can't be null");
			throw new Exception("Discussion Id is can't be null");
		}
		return comment;
	}

	private boolean verifyUserBoughtProduct(User user, String discussionId) {

		List<com.sarvah.mbg.domain.ordermgmt.Order> orders = userOrderDAO
				.findByUserInfo_MongoUserId(user.getId());
		for (com.sarvah.mbg.domain.ordermgmt.Order order : orders) {
			String itemStatus = "Delivered";
			OrderDetails orderDetails = userOrderDetailsDAO
					.findByOrderAndItemIdAndItemStatusDescription(order,
							discussionId, itemStatus);
			if (orderDetails != null) {
				// logged in user already bought the product and the product is
				// in delivered state.
				logger.info("logged in user already bought the product and the product is in delivered state");
				return true;
			}
		}

		return false;
	}

	/**
	 * Method to update Comment information
	 * 
	 * @param ratingval
	 * @param desc
	 * @param slug
	 * @param text
	 * @param expert
	 * @param discussionId
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Comment updateComment(String ratingval, String desc, String slug,
			String text, String expert, String discussionId, String commentId)
			throws Exception {
		// context.getId() Implimentation
		User user = commentUserDAO.findOne("5666a85929bfa3183ab40068");

		Comment comment = new Comment();
		if (StringUtils.isNotBlank(discussionId)) {
			Product product = commentProductDAO.findOne(discussionId);
			if (product != null && commentId != null) {
				comment = commentDAO.findOne(commentId);
				if (comment != null) {

					if (ratingval != null && desc != null) {
						Rating rating = comment.getRating();
						rating.setRatingVal(Double.parseDouble(ratingval));
						if (desc != null) {

							rating.getDesc().setLang("Eng");
							rating.getDesc().setVal(desc);
							comment.setRating(rating);
						}
					}
					if (text != null) {
						comment.setText(text);
					}

					comment.setDiscussionId(discussionId);

					if (StringUtils.isNotBlank(slug)) {
						comment.setSlug(slug);
					}

					comment.setCommentType(CommentType.PRODUCT);

					// User user = commentUserDAO.findOne("1");
					if (user != null) {
						Author author = new Author();
						author.setUserId(user.getId());
						author.setName(user.getFullName());
						if (StringUtils.contains(expert, "true")) {
							author.setExpert(true);
						} else {
							author.setExpert(false);
						}
						comment.setAuthor(author);
					}

				} else {
					comment = null;
					throw new Exception("CommentId Null");
				}
				commentDAO.save(comment);
			} else {
				User users = commentUserDAO.findOne(discussionId);
				if (users != null && commentId != null) {
					comment = commentDAO.findOne(commentId);
					if (comment != null) {

						if (ratingval != null && desc != null) {
							Rating rating = comment.getRating();
							rating.setRatingVal(Double.parseDouble(ratingval));
							if (desc != null) {

								rating.getDesc().setLang("Eng");
								rating.getDesc().setVal(desc);
								comment.setRating(rating);
							}
						}
						if (text != null) {
							comment.setText(text);
						}

						comment.setDiscussionId(discussionId);

						if (StringUtils.isNotBlank(slug)) {
							comment.setSlug(slug);
						}

						comment.setCommentType(CommentType.USER);

						if (user != null) {
							Author author = new Author();
							author.setUserId(user.getId());
							author.setName(user.getFullName());
							if (StringUtils.contains(expert, "true")) {
								author.setExpert(true);
							} else {
								author.setExpert(false);
							}
							comment.setAuthor(author);
						}

					} else {
						comment = null;
						throw new Exception("CommentId Null");
					}
				}
				commentDAO.save(comment);
			}

		} else {
			comment = null;
			logger.info("DiscussionId can't be null");
			throw new Exception("DiscussionId can't be null");
		}
		return comment;

	}

	@Override
	public String remove(String productId, String commentId) throws Exception {
		if (StringUtils.isNotBlank(productId)
				&& StringUtils.isNotBlank(commentId)) {
			Comment comment = null;
			Product product = commentProductDAO.findOne(productId);
			if (product != null) {
				comment = commentDAO.findOne(commentId);
				if (comment != null) {
					commentDAO.delete(comment);
				} else {
					logger.info("Comment doesn't exists");
				}
			} else {
				User users = commentUserDAO.findOne(productId);
				if (users != null) {
					comment = commentDAO.findOne(commentId);
					if (comment != null) {
						commentDAO.delete(commentId);
					} else {
						logger.info("Comment doesn't exists");
					}
				}
			}
		} else {
			logger.info("CommentId can't be null");
			throw new Exception("CommentId can't be null");
		}
		return commentId;
	}

}
