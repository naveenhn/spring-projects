/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.review.Author;
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.review.Rating;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.reviews.dao.mongo.CommentDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu
 *
 */
public class CommentPopulatorBean {
	UserDAO userDAO;
	ProductDAO productDAO;
	CommentDAO commentDAO;

	CommentPopulatorBean(UserDAO userDAO, ProductDAO productDAO,
			CommentDAO commentDAO) {
		this.userDAO = userDAO;
		this.productDAO = productDAO;
		this.commentDAO = commentDAO;
	}

	public void initComment() {
		commentDAO.deleteAll();

//		Comment comment1 = new Comment();
//
//		List<User> user = userDAO.findByUsername("admin@havells.com",
//				new PageRequest(0, 10));
//		comment1.setDiscussionId(user.get(0).getId());
//		comment1.setText("Jaquar is good....");
//
//		Rating rating1 = new Rating();
//		rating1.setRatingVal(4.0);
//		Description desc1 = new Description();
//		desc1.setLang("eng");
//		desc1.setVal("we can give 4.0 rating to this user...");
//		rating1.setDesc(desc1);
//
//		comment1.setRating(rating1);
//
//		Author author1 = new Author();
//		author1.setUserId("15728");
//		author1.setName("Ramesh");
//		Image image = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author1.setImage(image);
//
//		comment1.setAuthor(author1);
//
//		commentDAO.insert(comment1);
//
//		Comment comment2 = new Comment();
//
//		List<User> user1 = userDAO.findByUsername("admin@godrej.com",
//				new PageRequest(0, 10));
//		comment2.setDiscussionId(user1.get(0).getId());
//		comment2.setText("Jaquar is good....");
//
//		Rating rating2 = new Rating();
//		rating2.setRatingVal(2.5);
//		Description desc2 = new Description();
//		desc2.setLang("eng");
//		desc2.setVal("we can give 4.0 rating to this user...");
//		rating2.setDesc(desc2);
//
//		comment2.setRating(rating2);
//
//		Author author2 = new Author();
//		author2.setUserId("15728");
//		author2.setName("Ramesh");
//		Image image2 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author2.setImage(image2);
//
//		comment2.setAuthor(author2);
//
//		commentDAO.insert(comment2);
//
//		Comment comment3 = new Comment();
//
//		List<Product> product = productDAO.findByNameLike("Jaguar - XYZ");
//		comment3.setDiscussionId(product.get(0).getId());
//		comment3.setText("Jaguar - XYZ product is good");
//
//		Rating rating3 = new Rating();
//		rating3.setRatingVal(4.5);
//		Description desc3 = new Description();
//		desc3.setLang("eng");
//		desc3.setVal("we can give 4.5 rating to this product...");
//		rating3.setDesc(desc3);
//
//		comment3.setRating(rating3);
//
//		Author author3 = new Author();
//		author3.setUserId("23842");
//		author3.setName("suresh");
//		Image image1 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author3.setImage(image1);
//
//		comment3.setAuthor(author3);
//
//		commentDAO.insert(comment3);
//
//		Comment comment4 = new Comment();
//
//		List<User> user4 = userDAO.findByUsername("admin@cera.com",
//				new PageRequest(0, 10));
//		comment4.setDiscussionId(user4.get(0).getId());
//		comment4.setText("Jaquar is good....");
//
//		Rating rating4 = new Rating();
//		rating4.setRatingVal(4.0);
//		Description desc4 = new Description();
//		desc4.setLang("eng");
//		desc4.setVal("we can give 4.0 rating to this user...");
//		rating4.setDesc(desc4);
//
//		comment4.setRating(rating4);
//
//		Author author4 = new Author();
//		author4.setUserId("15728");
//		author4.setName("Ramesh");
//		Image image4 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author4.setImage(image4);
//
//		comment4.setAuthor(author4);
//
//		commentDAO.insert(comment4);
//
//		Comment comment5 = new Comment();
//
//		List<User> user5 = userDAO.findByUsername("admin@godrej.com",
//				new PageRequest(0, 10));
//		comment5.setDiscussionId(user5.get(0).getId());
//		comment5.setText("Jaquar is good....");
//
//		Rating rating5 = new Rating();
//		rating5.setRatingVal(4.0);
//		Description desc5 = new Description();
//		desc5.setLang("eng");
//		desc5.setVal("we can give 4.0 rating to this user...");
//		rating5.setDesc(desc5);
//
//		comment5.setRating(rating5);
//
//		Author author5 = new Author();
//		author5.setUserId("15728");
//		author5.setName("Ramesh");
//		Image image5 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author5.setImage(image5);
//
//		comment5.setAuthor(author5);
//
//		commentDAO.insert(comment5);
//		
//		Comment comment6 = new Comment();
//		
//		List<User> user6 = userDAO.findByUsername("admin@havells.com",
//				new PageRequest(0, 10));
//		comment6.setDiscussionId(user6.get(0).getId());
//		comment6.setText("Jaquar is good....");
//
//		Rating rating6 = new Rating();
//		rating6.setRatingVal(3.0);
//		Description desc6 = new Description();
//		desc6.setLang("eng");
//		desc6.setVal("we can give 4.0 rating to this user...");
//		rating6.setDesc(desc6);
//
//		comment6.setRating(rating6);
//
//		Author author6 = new Author();
//		author6.setUserId("15728");
//		author6.setName("Ramesh");
//		Image image6 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author6.setImage(image6);
//
//		comment6.setAuthor(author6);
//
//		commentDAO.insert(comment6);
//
//		Comment comment7 = new Comment();
//
//		List<Product> product7 = productDAO.findByNameLike("Jaguar - XYZ");
//		comment7.setDiscussionId(product7.get(0).getId());
//		comment7.setText("Jaguar - XYZ product is good");
//
//		Rating rating7 = new Rating();
//		rating7.setRatingVal(2.5);
//		Description desc7 = new Description();
//		desc7.setLang("eng");
//		desc7.setVal("we can give 4.5 rating to this product...");
//		rating7.setDesc(desc7);
//
//		comment7.setRating(rating7);
//
//		Author author7 = new Author();
//		author7.setUserId("23842");
//		author7.setName("suresh");
//		Image image7 = new Image("img1",
//				"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
//		author7.setImage(image7);
//
//		comment7.setAuthor(author7);
//
//		commentDAO.insert(comment7);
	}

}
