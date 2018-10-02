package com.sarvah.mbg.config.populator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductQuantityTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.common.asset.Video;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;
import com.sarvah.mbg.domain.mongo.review.Rating;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.recentlyviewed.dao.mongo.RecentlyViewedDAO;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

public class ProductPopulatorBean {

	ProductDAO productDAO;

	CategoryDAO categoryDAO;
	SubCategoryDAO subCategoryDAO;
	ProductTypeDAO productTypeDAO;
	UserDAO userDAO;
	RecentlyViewedDAO recentlyViewedDAO;
	RoleDAO roleDAO;
	UserPackageDAO userPackageDAO;
	BrandDAO brandDAO;
	ProductQuantityTypeDAO productQuantityTypeDAO;

	public ProductPopulatorBean(ProductDAO productDAO, CategoryDAO categoryDAO,
			SubCategoryDAO subCategoryDAO, ProductTypeDAO productTypeDAO,
			UserDAO userDAO, RecentlyViewedDAO recentlyViewedDAO,
			RoleDAO roleDAO, UserPackageDAO userPackageDAO, BrandDAO brandDAO,
			ProductQuantityTypeDAO productQuantityTypeDAO) {
		this.productDAO = productDAO;
		this.categoryDAO = categoryDAO;
		this.subCategoryDAO = subCategoryDAO;
		this.productTypeDAO = productTypeDAO;
		this.userDAO = userDAO;
		this.recentlyViewedDAO = recentlyViewedDAO;
		this.roleDAO = roleDAO;
		this.userPackageDAO = userPackageDAO;
		this.brandDAO = brandDAO;
		this.productQuantityTypeDAO = productQuantityTypeDAO;

	}

	public void initProducts() {
		List<User> users = userDAO.findByUsername("admin@jaquar.com",
				new PageRequest(0, 10));
		List<ProductType> productTypes = productTypeDAO.findByName("Ivory");

		List<ProductQuantityType> productQuantityTypes = productQuantityTypeDAO
				.findAll();

		if (users != null && productTypes != null) {
			// productDAO.deleteAll();
			// *** Product1 ***
			Product product = new Product();
			product.setId("1");
			product.setName("Jaguar - XYZ");
			product.setSkuId("Jaq-bath-dadkda12345643");
			ProductQuantityType productQuantityType1 = new ProductQuantityType();
			productQuantityType1.setName("Unit");
			product.setQuantityType(productQuantityType1);
			product.setModel("Jaguar - XYZ+Jaq-bath-dadkda12345643 ");
			product.setLongName("Jaguar - XYZ long name.....");

			ProductBrand productBrand = brandDAO.findByName("Jaquar");
			product.setBrand(productBrand);

			Description desc1 = new Description("en",
					"Jaquar super fablous product1");
			product.setDesc(desc1);
			Price price = new Price();
			price.setCurrency("Rupee");
			price.setMrp(30000);
			product.setPrice(price);

			List<SubCategory> subCategories = subCategoryDAO.findByName("Tubs");
			product.setSubcategories(new HashSet<SubCategory>(subCategories));

			ProductAsset productAsset1 = new ProductAsset();
			List<Image> imageList1 = new ArrayList<>();
			Image image1 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image2 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image3 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image4 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image5 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList1.add(image1);
			imageList1.add(image2);
			imageList1.add(image3);
			imageList1.add(image4);
			imageList1.add(image5);
			productAsset1.setImages(imageList1);
			// video
			Video video = new Video();
			video.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset1.setVideo(video);

			List<File> relatedFiles = new ArrayList<>();
			File file11 = new File();
			file11.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/J/JA/provider_dealers.csv");
			file11.setName("provider_dealers.csv");

			relatedFiles.add(file11);
			productAsset1.setRelatedFiles(relatedFiles);

			product.setAssets(productAsset1);

			ProductAsset custBought = new ProductAsset();
			List<Image> custBoughtList = new ArrayList<>();
			Image boughtImage1 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image boughtImage2 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image boughtImage3 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image boughtImage4 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image boughtImage5 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			custBoughtList.add(boughtImage1);
			custBoughtList.add(boughtImage2);
			custBoughtList.add(boughtImage3);
			custBoughtList.add(boughtImage4);
			custBoughtList.add(boughtImage5);
			custBought.setImages(custBoughtList);
			product.setAssets(custBought);

			ProductAsset custLiked = new ProductAsset();
			List<Image> custLikedList = new ArrayList<>();
			Image likedImage1 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image likedImage2 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image likedImage3 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image likedImage4 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image likedImage5 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			custLikedList.add(likedImage1);
			custLikedList.add(likedImage2);
			custLikedList.add(likedImage3);
			custLikedList.add(likedImage4);
			custLikedList.add(likedImage5);
			custLiked.setImages(custLikedList);
			product.setAssets(custLiked);

			product.setAccessory(true);
			Set<String> relatedProductIds = new HashSet<>();
			relatedProductIds.add("2");
			relatedProductIds.add("3");
			product.setRelatedProducts(relatedProductIds);

			Set<String> accessoriesProductIds = new HashSet<>();
			accessoriesProductIds.add("4");
			accessoriesProductIds.add("5");
			product.setAccessories(accessoriesProductIds);

			// features
			ProductAttribute features = new ProductAttribute();
			features.setMultiValued(true);
			features.setKey("features");
			Set<String> values = new HashSet<>();
			values.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			values.add("Phasellus condimentum quam a ex euismod varius.");
			values.add("Quisque in odio nec diam hendrerit egestas et et urna.");
			values.add("Donec ullamcorper mi in sapien ultrices porttitor.");
			values.add("Nam iaculis urna vitae egestas fringilla.");
			features.setValues(values);
			product.setFeatures(features);

			// attributes
			// color
			Set<ProductAttribute> attributes = new HashSet<>();

			ProductAttribute productAttribute = new ProductAttribute();
			productAttribute.setMultiValued(true);
			productAttribute.setKey("colors");
			Set<String> values1 = new HashSet<>();
			values1.add("red(#FF0000)");
			values1.add("Black(#000000)");
			values1.add("Green(#00FF00)");
			productAttribute.setValues(values1);
			attributes.add(productAttribute);
			// width
			ProductAttribute productAttribute1 = new ProductAttribute();
			productAttribute1.setMultiValued(true);
			productAttribute1.setKey("width");
			Set<String> values2 = new HashSet<>();
			values2.add("66.5cm");
			productAttribute1.setValues(values2);
			attributes.add(productAttribute1);

			// height
			ProductAttribute productAttribute2 = new ProductAttribute();
			productAttribute2.setMultiValued(true);
			productAttribute2.setKey("height");
			Set<String> values3 = new HashSet<>();
			values3.add("72.5 cm");
			productAttribute2.setValues(values3);
			attributes.add(productAttribute2);

			product.setAttributes(attributes);

			// faqs
			Set<ProductFaq> faqs = new HashSet<>();
			String question1 = "What are good materials for the walls?";
			String answer1 = "Exterior plywood or boards are both suitable. "
					+ "Boards are usually overlapped to allow rain to run "
					+ "off without penetrating the wall - a technique called "
					+ "shiplapping. If the frame is very lightweight, plywood is "
					+ "useful as a base layer to make the structure much more rigid. ";

			String question2 = "What are good materials for the frame?";
			String answer2 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber. ";

			ProductFaq productFaq = new ProductFaq(question1, answer1);
			ProductFaq productFaq1 = new ProductFaq(question2, answer2);
			faqs.add(productFaq);
			faqs.add(productFaq1);
			product.setFaqs(faqs);
			// file
			File file = new File("file1",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file1.doc");
			product.setTcDoc(file);

			product.setStatus(ProductStatus.IN_BUILDONN);
			ConsolidatedRating consolidatedRating1 = new ConsolidatedRating();
			consolidatedRating1.setOneRatingUserCount(1);
			consolidatedRating1.setTwoRatingUserCount(0);
			consolidatedRating1.setThreeRatingUserCount(0);
			consolidatedRating1.setFourRatingUserCount(0);
			consolidatedRating1.setFiveRatingUserCount(1);
			Rating avgRating = new Rating();
			avgRating.setRatingVal(3.0);
			consolidatedRating1.setAvgRating(avgRating);
			product.setConsolidatedRating(consolidatedRating1);
			product.setCreatedBy("564b0b53a4ec709dc796e827");
			product.setCreatedDate(new Date());

			// *** Product 2 ***
			Product product1 = new Product();
			product1.setId("2");
			product1.setName("Cera - ABC");
			product1.setSkuId("qwert12345");
			ProductQuantityType productQuantityType2 = new ProductQuantityType();
			productQuantityType2.setName("Unit");
			product1.setQuantityType(productQuantityType2);
			product1.setModel("Cera - ABC+qwert12345");
			product1.setLongName("Cera - ABC long name.....");
			ProductBrand productBrand1 = brandDAO.findByName("Cera");
			product1.setBrand(productBrand1);
			Description desc3 = new Description("en",
					"Jaquar super fablous product2");
			product1.setDesc(desc3);
			Price price1 = new Price();
			price1.setCurrency("Rupee");
			price1.setMrp(50000);
			product1.setPrice(price1);

			product1.setSubcategories(new HashSet<SubCategory>(subCategories));

			ProductAsset productAsset2 = new ProductAsset();
			List<Image> imageList2 = new ArrayList<>();
			Image image6 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image7 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image8 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image9 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image10 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList2.add(image6);
			imageList2.add(image7);
			imageList2.add(image8);
			imageList2.add(image9);
			imageList2.add(image10);
			productAsset2.setImages(imageList2);
			// video
			Video video1 = new Video();
			video.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset2.setVideo(video1);

			List<File> relatedFiles1 = new ArrayList<>();
			File file22 = new File();
			file22.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/C/CE/provider_dealers.csv");
			file22.setName("provider_dealers.csv");

			relatedFiles1.add(file22);
			productAsset2.setRelatedFiles(relatedFiles1);

			product1.setAssets(productAsset2);

			ProductAsset custBought2 = new ProductAsset();
			List<Image> custBoughtList2 = new ArrayList<>();
			Image boughtImage6 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image boughtImage7 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image boughtImage8 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image boughtImage9 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image boughtImage10 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			custBoughtList2.add(boughtImage6);
			custBoughtList2.add(boughtImage7);
			custBoughtList2.add(boughtImage8);
			custBoughtList2.add(boughtImage9);
			custBoughtList2.add(boughtImage10);
			custBought2.setImages(custBoughtList2);
			product1.setAssets(custBought2);

			ProductAsset custLiked2 = new ProductAsset();
			List<Image> custLikedList2 = new ArrayList<>();
			Image likedImage6 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image likedImage7 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image likedImage8 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image likedImage9 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image likedImage10 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			custLikedList2.add(likedImage6);
			custLikedList2.add(likedImage7);
			custLikedList2.add(likedImage8);
			custLikedList2.add(likedImage9);
			custLikedList2.add(likedImage10);
			custLiked2.setImages(custLikedList);
			product1.setAssets(custLiked2);

			product1.setAccessory(true);
			Set<String> relatedProductIds1 = new HashSet<>();
			relatedProductIds1.add("3");
			relatedProductIds1.add("4");
			product1.setRelatedProducts(relatedProductIds1);

			Set<String> accessoriesProductIds1 = new HashSet<>();
			accessoriesProductIds1.add("5");
			accessoriesProductIds1.add("6");
			product1.setAccessories(accessoriesProductIds1);

			product1.setStatus(ProductStatus.IN_BUILDONN);

			// features
			ProductAttribute features1 = new ProductAttribute();
			features1.setMultiValued(true);
			features1.setKey("features");
			Set<String> values4 = new HashSet<>();
			values4.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			values4.add("Phasellus condimentum quam a ex euismod varius.");
			values4.add("Quisque in odio nec diam hendrerit egestas et et urna.");
			values4.add("Donec ullamcorper mi in sapien ultrices porttitor.");
			values4.add("Nam iaculis urna vitae egestas fringilla.");
			features.setValues(values4);
			product1.setFeatures(features1);

			// attributes
			Set<ProductAttribute> attributes3 = new HashSet<>();

			ProductAttribute productAttribute3 = new ProductAttribute();
			productAttribute3.setMultiValued(true);
			productAttribute3.setKey("colors");
			Set<String> values5 = new HashSet<>();
			values5.add("red(#FF0000)");
			values5.add("Black(#000000)");
			values5.add("Green(#00FF00)");
			productAttribute3.setValues(values5);
			attributes3.add(productAttribute3);

			// width
			ProductAttribute width = new ProductAttribute();
			width.setMultiValued(true);
			width.setKey("width");
			Set<String> values6 = new HashSet<>();
			values6.add("66.5cm");
			width.setValues(values6);
			attributes3.add(width);

			// height
			ProductAttribute height = new ProductAttribute();
			height.setMultiValued(true);
			height.setKey("height");
			Set<String> values7 = new HashSet<>();
			values7.add("72.5 cm");
			height.setValues(values7);
			attributes3.add(height);

			product1.setAttributes(attributes3);

			// faqs
			Set<ProductFaq> faqs1 = new HashSet<>();
			String question3 = "What are good materials for the walls?";
			String answer3 = "Exterior plywood or boards are both suitable. "
					+ "Boards are usually overlapped to allow rain to run "
					+ "off without penetrating the wall - a technique called "
					+ "shiplapping. If the frame is very lightweight, plywood is "
					+ "useful as a base layer to make the structure much more rigid. "
					+ "This can then be faced with other materials if required";

			String question4 = "What are good materials for the frame?";
			String answer4 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber "
					+ "frame effect, but they will add a lot of weight to the structure.";

			ProductFaq productFaq2 = new ProductFaq(question3, answer3);
			ProductFaq productFaq3 = new ProductFaq(question4, answer4);
			faqs1.add(productFaq2);
			faqs1.add(productFaq3);
			product1.setFaqs(faqs1);
			// file
			File file1 = new File("file2",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file2.doc");
			product1.setTcDoc(file1);
			ConsolidatedRating consolidatedRating2 = new ConsolidatedRating();
			consolidatedRating2.setOneRatingUserCount(0);
			consolidatedRating2.setTwoRatingUserCount(0);
			consolidatedRating2.setThreeRatingUserCount(0);
			consolidatedRating2.setFourRatingUserCount(2);
			consolidatedRating2.setFiveRatingUserCount(0);
			Rating avgRating2 = new Rating();
			avgRating2.setRatingVal(4.0);
			consolidatedRating2.setAvgRating(avgRating2);
			product1.setConsolidatedRating(consolidatedRating2);
			product1.setCreatedBy("3");
			product1.setCreatedDate(new Date());

			// *** Product 3 ***
			Product product2 = new Product();
			product2.setId("3");
			product2.setName("Cera - XYZ");
			product2.setSkuId("qaz1234567");
			ProductQuantityType productQuantityType3 = new ProductQuantityType();
			productQuantityType3.setName("Unit");
			product2.setQuantityType(productQuantityType3);
			product2.setModel("Cera - XYZ+qaz1234567");
			product2.setLongName("Cera - XYZ long name.....");

			ProductBrand productBrand2 = brandDAO.findByName("Cera");
			product2.setBrand(productBrand2);

			Description desc5 = new Description("en",
					"Jaquar super fablous product3");
			product2.setDesc(desc5);
			Price price2 = new Price();
			price2.setCurrency("Rupee");
			price2.setMrp(90000);
			product2.setPrice(price2);
			product2.setSubcategories(new HashSet<SubCategory>(subCategories));

			ProductAsset productAsset3 = new ProductAsset();
			List<Image> imageList3 = new ArrayList<>();
			Image image11 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image12 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image13 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image14 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image15 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList3.add(image11);
			imageList3.add(image12);
			imageList3.add(image13);
			imageList3.add(image14);
			imageList3.add(image15);
			productAsset3.setImages(imageList3);
			// video
			Video video2 = new Video();
			video.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset3.setVideo(video2);

			List<File> relatedFiles2 = new ArrayList<>();
			File file33 = new File();
			file33.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/C/CE/provider_dealers.csv");
			file33.setName("provider_dealers.csv");

			relatedFiles1.add(file33);
			productAsset3.setRelatedFiles(relatedFiles2);

			product2.setAssets(productAsset3);
			product2.setAccessory(true);
			Set<String> relatedProductIds2 = new HashSet<>();
			relatedProductIds2.add("1");
			relatedProductIds2.add("2");
			product2.setRelatedProducts(relatedProductIds2);

			Set<String> accessoriesProductIds2 = new HashSet<>();
			accessoriesProductIds2.add("4");
			accessoriesProductIds2.add("5");
			productAsset3.setImages(imageList3);
			product2.setAccessories(accessoriesProductIds2);
			// features
			ProductAttribute features3 = new ProductAttribute();
			features3.setMultiValued(true);
			features3.setKey("features");
			Set<String> featureval = new HashSet<>();
			featureval
					.add("Curabitur a ipsum posuere, luctus leo vitae, bibendum erat..");
			featureval
					.add("Quisque imperdiet nisl sit amet felis interdum, eu volutpat libero dictum.");
			featureval
					.add("Fusce posuere nunc laoreet lectus vestibulum, a pharetra nisi placerat.");
			featureval
					.add("Duis laoreet sapien ut purus luctus, nec pretium lacus consectetur.");
			featureval.add("Nam iaculis urna vitae egestas fringilla.");
			features3.setValues(featureval);
			product2.setFeatures(features3);

			// attributes
			Set<ProductAttribute> attributes2 = new HashSet<>();

			ProductAttribute productAttribute4 = new ProductAttribute();
			productAttribute4.setMultiValued(true);
			productAttribute4.setKey("colors");
			Set<String> color = new HashSet<>();
			color.add("red(#FF0000)");
			color.add("Black(#000000)");
			color.add("Green(#00FF00)");
			productAttribute4.setValues(color);
			attributes2.add(productAttribute4);
			// width
			ProductAttribute width1 = new ProductAttribute();
			width1.setMultiValued(true);
			width1.setKey("width");
			Set<String> values8 = new HashSet<>();
			values8.add("66.5cm");
			width1.setValues(values8);
			attributes2.add(width1);

			// height
			ProductAttribute height1 = new ProductAttribute();
			height1.setMultiValued(true);
			height1.setKey("height");
			Set<String> values9 = new HashSet<>();
			values9.add("72.5 cm");
			height1.setValues(values9);
			attributes2.add(height1);

			product2.setAttributes(attributes2);

			// faqs
			Set<ProductFaq> faqs2 = new HashSet<>();
			String question5 = "What are good material for the supports?";
			String answer5 = "Regular wood from a building supply store is ideal,"
					+ "as it is of fixed dimension and will have a predictable strength."
					+ "Self-harvested wood can be used, but is weaker when green and should be milled"
					+ " to reveal any hidden defects that could cause a failure";

			String question6 = "What are good materials for the frame?";
			String answer6 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber "
					+ "frame effect, but they will add a lot of weight to the structure.";

			ProductFaq productFaq4 = new ProductFaq(question5, answer5);
			ProductFaq productFaq5 = new ProductFaq(question6, answer6);
			faqs2.add(productFaq4);
			faqs2.add(productFaq5);
			product2.setFaqs(faqs2);
			// file
			File file2 = new File("file3",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file3.doc");
			product2.setTcDoc(file2);
			product2.setStatus(ProductStatus.IN_BUILDONN);
			ConsolidatedRating consolidatedRating3 = new ConsolidatedRating();
			consolidatedRating3.setOneRatingUserCount(0);
			consolidatedRating3.setTwoRatingUserCount(0);
			consolidatedRating3.setThreeRatingUserCount(1);
			consolidatedRating3.setFourRatingUserCount(1);
			consolidatedRating3.setFiveRatingUserCount(0);
			Rating avgRating3 = new Rating();
			avgRating3.setRatingVal(3.5);
			consolidatedRating3.setAvgRating(avgRating3);
			product2.setConsolidatedRating(consolidatedRating3);
			product2.setCreatedBy("3");
			product2.setCreatedDate(new Date());

			// *** Product 4 ***
			Product product3 = new Product();
			product3.setId("4");
			product3.setName("Hindware - XYZ");
			product3.setSkuId("mbg123");
			ProductQuantityType productQuantityType4 = new ProductQuantityType();
			productQuantityType4.setName("Unit");
			product3.setQuantityType(productQuantityType4);
			product3.setModel("Hindware - XYZ+mbg123");
			product3.setLongName("Hindware - xyz long name.....");

			ProductBrand productBrand3 = brandDAO.findByName("Hindware");
			product3.setBrand(productBrand3);

			Description desc7 = new Description("en",
					"Jaquar super fablous product4");
			product3.setDesc(desc7);
			Price price3 = new Price();
			price3.setCurrency("Rupee");
			price3.setMrp(25000);
			product3.setPrice(price3);

			product3.setSubcategories(new HashSet<SubCategory>(subCategories));

			ProductAsset productAsset4 = new ProductAsset();
			List<Image> imageList4 = new ArrayList<>();
			Image image16 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image17 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image18 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image19 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image20 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList4.add(image16);
			imageList4.add(image17);
			imageList4.add(image18);
			imageList4.add(image19);
			imageList4.add(image20);
			productAsset4.setImages(imageList4);
			// video
			Video video3 = new Video();
			video3.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset4.setVideo(video3);

			List<File> relatedFiles3 = new ArrayList<>();
			File file44 = new File();
			file44.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/H/HI/provider_dealers.csv");
			file44.setName("provider_dealers.csv");

			relatedFiles1.add(file44);
			productAsset4.setRelatedFiles(relatedFiles3);

			product3.setAssets(productAsset4);
			product3.setAccessory(true);
			Set<String> relatedProductIds3 = new HashSet<>();
			relatedProductIds3.add("2");
			relatedProductIds3.add("3");
			product3.setRelatedProducts(relatedProductIds3);

			Set<String> accessoriesProductIds3 = new HashSet<>();
			accessoriesProductIds3.add("5");
			accessoriesProductIds3.add("6");
			product3.setAccessories(accessoriesProductIds3);

			product3.setStatus(ProductStatus.IN_BUILDONN);
			ConsolidatedRating consolidatedRating4 = new ConsolidatedRating();
			consolidatedRating4.setOneRatingUserCount(0);
			consolidatedRating4.setTwoRatingUserCount(0);
			consolidatedRating4.setThreeRatingUserCount(1);
			consolidatedRating4.setFourRatingUserCount(1);
			consolidatedRating4.setFiveRatingUserCount(0);
			Rating avgRating4 = new Rating();
			avgRating4.setRatingVal(3.5);
			consolidatedRating4.setAvgRating(avgRating4);
			product3.setConsolidatedRating(consolidatedRating4);
			product3.setCreatedBy("3");

			// features
			ProductAttribute features4 = new ProductAttribute();
			features4.setMultiValued(true);
			features4.setKey("features");
			Set<String> featureval2 = new HashSet<>();
			featureval2
					.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			featureval2.add("Phasellus condimentum quam a ex euismod varius.");
			featureval2
					.add("Quisque in odio nec diam hendrerit egestas et et urna.");
			featureval2
					.add("Donec ullamcorper mi in sapien ultrices porttitor.");
			featureval2.add("Nam iaculis urna vitae egestas fringilla.");
			features4.setValues(featureval2);
			product3.setFeatures(features4);

			// attributes
			Set<ProductAttribute> attributes4 = new HashSet<>();

			ProductAttribute color2 = new ProductAttribute();
			color2.setMultiValued(true);
			color2.setKey("colors");
			Set<String> colorval = new HashSet<>();
			colorval.add("red(#FF0000)");
			colorval.add("Black(#000000)");
			colorval.add("Green(#00FF00)");
			color2.setValues(colorval);
			attributes4.add(color2);

			// width
			ProductAttribute width2 = new ProductAttribute();
			width2.setMultiValued(true);
			width2.setKey("width");
			Set<String> widthval = new HashSet<>();
			widthval.add("66.5cm");
			width2.setValues(widthval);
			attributes4.add(width2);

			// height
			ProductAttribute height2 = new ProductAttribute();
			height2.setMultiValued(true);
			height2.setKey("height");
			Set<String> heightval = new HashSet<>();
			heightval.add("72.5 cm");
			height2.setValues(heightval);
			attributes4.add(height2);

			product3.setAttributes(attributes4);

			// faqs
			Set<ProductFaq> faqs3 = new HashSet<>();
			String question7 = "What are good materials for the roof?";
			String answer7 = "Glass is clearer and is scratch resistant, "
					+ "but plastic will not shatter into dangerous fragments if broken,"
					+ " either from the inside by an occupant, or by a falling branch from outside.";

			String question8 = "What are good materials for the frame?";
			String answer8 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber. ";

			ProductFaq productFaq6 = new ProductFaq(question7, answer7);
			ProductFaq productFaq7 = new ProductFaq(question8, answer8);
			faqs3.add(productFaq6);
			faqs3.add(productFaq7);
			product3.setFaqs(faqs3);
			// file
			File file3 = new File("file3",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file3.doc");
			product3.setTcDoc(file3);
			Set<ProductType> prodTypes = new HashSet<>();
			prodTypes.add(productTypes.get(0));
			product.setQuantityType(productQuantityTypes.get(0));
			product.setProductTypes(prodTypes);
			product1.setProductTypes(prodTypes);
			product1.setQuantityType(productQuantityTypes.get(0));

			product2.setProductTypes(prodTypes);
			product2.setQuantityType(productQuantityTypes.get(0));
			product3.setProductTypes(prodTypes);
			product3.setQuantityType(productQuantityTypes.get(0));
			product3.setCreatedDate(new Date());

			productDAO.insert(product);
			productDAO.insert(product1);
			productDAO.insert(product2);
			productDAO.insert(product3);

			// *** Product 5 ***
			Product product4 = new Product();
			product4.setId("5");
			product4.setName("Toto - MNO");
			product4.setSkuId("Jaq-bath-dadkda1234564345");
			ProductQuantityType productQuantityType5 = new ProductQuantityType();
			productQuantityType5.setName("Unit");
			product4.setQuantityType(productQuantityType5);
			product4.setModel("Toto - MNO+Jaq-bath-dadkda1234564345");
			product4.setLongName("Toto - MNO long name.....");

			ProductBrand productBrand4 = brandDAO.findByName("Toto");
			product4.setBrand(productBrand4);

			Description desc4 = new Description("en",
					"Jaquar super fablous product5");
			product4.setDesc(desc4);
			Price price4 = new Price();
			price4.setCurrency("Rupee");
			price4.setMrp(66000);
			product4.setPrice(price4);

			List<SubCategory> subCategories4 = subCategoryDAO
					.findByName("Tubs");
			product4.setSubcategories(new HashSet<SubCategory>(subCategories4));

			ProductAsset productAsset41 = new ProductAsset();
			List<Image> imageList41 = new ArrayList<>();
			Image image41 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image42 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image43 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image44 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image45 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList41.add(image41);
			imageList41.add(image42);
			imageList41.add(image43);
			imageList41.add(image44);
			imageList41.add(image45);
			productAsset41.setImages(imageList41);

			// video
			Video video4 = new Video();
			video4.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset41.setVideo(video4);

			List<File> relatedFiles4 = new ArrayList<>();
			File file55 = new File();
			file55.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/T/TO/provider_dealers.csv");
			file55.setName("provider_dealers.csv");

			relatedFiles1.add(file55);
			productAsset41.setRelatedFiles(relatedFiles4);

			product4.setAssets(productAsset41);

			product4.setAccessory(true);

			Set<String> relatedProductIds4 = new HashSet<>();
			relatedProductIds4.add("3");
			relatedProductIds4.add("4");
			product4.setRelatedProducts(relatedProductIds4);

			Set<String> accessoriesProductIds4 = new HashSet<>();
			accessoriesProductIds4.add("1");
			accessoriesProductIds4.add("2");
			product4.setAccessories(accessoriesProductIds4);

			// features
			ProductAttribute features41 = new ProductAttribute();
			features41.setMultiValued(true);
			features41.setKey("features");
			Set<String> values41 = new HashSet<>();
			values41.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			values41.add("Phasellus condimentum quam a ex euismod varius.");
			values41.add("Quisque in odio nec diam hendrerit egestas et et urna.");
			values41.add("Donec ullamcorper mi in sapien ultrices porttitor.");
			values41.add("Nam iaculis urna vitae egestas fringilla.");
			features41.setValues(values41);
			product4.setFeatures(features41);

			// attributes
			Set<ProductAttribute> attributes5 = new HashSet<>();
			ProductAttribute productAttribute5 = new ProductAttribute();
			productAttribute5.setMultiValued(true);
			productAttribute5.setKey("colors");
			Set<String> values411 = new HashSet<>();
			values411.add("red(#FF0000)");
			values411.add("Black(#000000)");
			values411.add("Green(#00FF00)");
			productAttribute5.setValues(values411);
			attributes5.add(productAttribute5);

			// width
			ProductAttribute width3 = new ProductAttribute();
			width3.setMultiValued(true);
			width3.setKey("width");
			Set<String> widthval1 = new HashSet<>();
			widthval1.add("66.5cm");
			width3.setValues(widthval1);
			attributes5.add(width3);

			// height
			ProductAttribute height3 = new ProductAttribute();
			height3.setMultiValued(true);
			height3.setKey("height");
			Set<String> heightval1 = new HashSet<>();
			heightval1.add("72.5 cm");
			height3.setValues(heightval);
			attributes5.add(height3);

			product4.setAttributes(attributes5);

			// faqs
			Set<ProductFaq> faqs4 = new HashSet<>();
			String question41 = "What are good materials for the walls?";
			String answer41 = "Exterior plywood or boards are both suitable. "
					+ "Boards are usually overlapped to allow rain to run "
					+ "off without penetrating the wall - a technique called "
					+ "shiplapping. If the frame is very lightweight, plywood is "
					+ "useful as a base layer to make the structure much more rigid. ";

			String question42 = "What are good materials for the frame?";
			String answer42 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber. ";

			ProductFaq productFaq41 = new ProductFaq(question41, answer41);
			ProductFaq productFaq412 = new ProductFaq(question42, answer42);
			faqs4.add(productFaq41);
			faqs4.add(productFaq412);
			product4.setFaqs(faqs4);
			// file
			File file4 = new File("file1",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file1.doc");
			product4.setTcDoc(file4);

			product4.setStatus(ProductStatus.IN_BUILDONN);
			ConsolidatedRating consolidatedRating5 = new ConsolidatedRating();
			consolidatedRating5.setOneRatingUserCount(0);
			consolidatedRating5.setTwoRatingUserCount(0);
			consolidatedRating5.setThreeRatingUserCount(0);
			consolidatedRating5.setFourRatingUserCount(0);
			consolidatedRating5.setFiveRatingUserCount(5);
			Rating avgRating5 = new Rating();
			avgRating5.setRatingVal(5.0);
			consolidatedRating5.setAvgRating(avgRating5);
			product4.setConsolidatedRating(consolidatedRating5);
			product4.setCreatedBy("564b0b53a4ec709dc796e827");
			product4.setQuantityType(productQuantityTypes.get(0));
			product4.setProductTypes(prodTypes);
			product4.setCreatedDate(new Date());
			productDAO.insert(product4);

			// *** Product 6 ***
			Product product5 = new Product();
			product5.setId("6");
			product5.setName("Hindware - PQR");
			product5.setSkuId("Jaq-bath-dadda1234564345");
			ProductQuantityType productQuantityType6 = new ProductQuantityType();
			productQuantityType6.setName("Unit");
			product5.setQuantityType(productQuantityType6);
			product5.setModel("Hindware - PQR+Jaq-bath-dadda1234564345");
			product5.setLongName("Hindware - PQR long name.....");

			ProductBrand productBrand5 = brandDAO.findByName("Hindware");
			product5.setBrand(productBrand5);

			Description desc51 = new Description("en",
					"Jaquar super fablous product6");
			product5.setDesc(desc51);
			Price price5 = new Price();
			price5.setCurrency("Rupee");
			price5.setMrp(48000);
			product5.setPrice(price5);

			List<SubCategory> subCategories5 = subCategoryDAO
					.findByName("Tubs");
			product5.setSubcategories(new HashSet<SubCategory>(subCategories5));

			ProductAsset productAsset51 = new ProductAsset();
			List<Image> imageList51 = new ArrayList<>();
			Image image51 = new Image("img1",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
			Image image52 = new Image("img2",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img2.jpg");
			Image image53 = new Image("img3",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img3.jpg");
			Image image54 = new Image("img4",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img4.jpg");
			Image image55 = new Image("img5",
					"https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img5.jpg");
			imageList51.add(image51);
			imageList51.add(image52);
			imageList51.add(image53);
			imageList51.add(image54);
			imageList51.add(image55);
			productAsset51.setImages(imageList51);

			// video
			Video video5 = new Video();
			video5.setUrl("https://www.youtube.com/watch?v=3MZIkY55fS0");
			productAsset51.setVideo(video5);

			List<File> relatedFiles5 = new ArrayList<>();
			File file66 = new File();
			file66.setUrl("https://mbgtest.blob.core.windows.net/files/catalog/product/H/HI/provider_dealers.csv");
			file66.setName("provider_dealers.csv");

			relatedFiles1.add(file66);
			productAsset51.setRelatedFiles(relatedFiles5);

			product5.setAssets(productAsset51);

			product5.setAccessory(true);

			Set<String> relatedProductIds5 = new HashSet<>();
			relatedProductIds5.add("4");
			relatedProductIds5.add("5");
			product5.setRelatedProducts(relatedProductIds5);

			Set<String> accessoriesProductIds5 = new HashSet<>();
			accessoriesProductIds5.add("2");
			accessoriesProductIds5.add("3");
			product5.setAccessories(accessoriesProductIds5);

			// features
			ProductAttribute features51 = new ProductAttribute();
			features51.setMultiValued(true);
			features51.setKey("features");
			Set<String> values51 = new HashSet<>();
			values51.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
			values51.add("Phasellus condimentum quam a ex euismod varius.");
			values51.add("Quisque in odio nec diam hendrerit egestas et et urna.");
			values51.add("Donec ullamcorper mi in sapien ultrices porttitor.");
			values51.add("Nam iaculis urna vitae egestas fringilla.");
			features51.setValues(values51);
			product5.setFeatures(features51);

			// attributes
			Set<ProductAttribute> attributes6 = new HashSet<>();

			ProductAttribute productAttribute6 = new ProductAttribute();
			productAttribute6.setMultiValued(true);
			productAttribute6.setKey("colors");
			Set<String> values511 = new HashSet<>();
			values511.add("red(#FF0000)");
			values511.add("Black(#000000)");
			values511.add("Green(#00FF00)");
			productAttribute6.setValues(values511);
			attributes6.add(productAttribute6);

			// width
			ProductAttribute width4 = new ProductAttribute();
			width4.setMultiValued(true);
			width4.setKey("width");
			Set<String> widthval2 = new HashSet<>();
			widthval2.add("66.5cm");
			width4.setValues(widthval2);
			attributes6.add(width4);

			// height
			ProductAttribute height4 = new ProductAttribute();
			height4.setMultiValued(true);
			height4.setKey("height");
			Set<String> heightval2 = new HashSet<>();
			heightval2.add("72.5 cm");
			height4.setValues(heightval2);
			attributes6.add(height4);

			product5.setAttributes(attributes6);

			// faqs
			Set<ProductFaq> faqs5 = new HashSet<>();
			String question51 = "What are good materials for the walls?";
			String answer51 = "Exterior plywood or boards are both suitable. "
					+ "Boards are usually overlapped to allow rain to run "
					+ "off without penetrating the wall - a technique called "
					+ "shiplapping. If the frame is very lightweight, plywood is "
					+ "useful as a base layer to make the structure much more rigid. ";

			String question52 = "What are good materials for the frame?";
			String answer52 = "Regular wood from a building supplier is well suited for framing a treehouse."
					+ "Oak or other hardwoods can be used to create a timber. ";

			ProductFaq productFaq51 = new ProductFaq(question51, answer51);
			ProductFaq productFaq512 = new ProductFaq(question52, answer52);
			faqs5.add(productFaq51);
			faqs5.add(productFaq512);
			product5.setFaqs(faqs5);
			// file
			File file5 = new File("file1",
					"https://mbgtest.blob.core.windows.net/file/catalog/product/A/AN/file1.doc");
			product5.setTcDoc(file5);

			product5.setStatus(ProductStatus.IN_BUILDONN);
			ConsolidatedRating consolidatedRating6 = new ConsolidatedRating();
			consolidatedRating6.setOneRatingUserCount(0);
			consolidatedRating6.setTwoRatingUserCount(0);
			consolidatedRating6.setThreeRatingUserCount(0);
			consolidatedRating6.setFourRatingUserCount(1);
			consolidatedRating6.setFiveRatingUserCount(1);
			Rating avgRating6 = new Rating();
			avgRating6.setRatingVal(4.5);
			consolidatedRating6.setAvgRating(avgRating6);
			product5.setConsolidatedRating(consolidatedRating6);
			product5.setCreatedBy("3");
			product5.setQuantityType(productQuantityTypes.get(0));
			product5.setProductTypes(prodTypes);
			product5.setCreatedDate(new Date());
			productDAO.insert(product5);
		}
	}
}