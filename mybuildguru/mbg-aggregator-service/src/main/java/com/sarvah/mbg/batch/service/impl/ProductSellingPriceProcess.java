/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.batch.dao.mongo.CommentDAO;
import com.sarvah.mbg.batch.dao.mongo.ProductDAO;
import com.sarvah.mbg.batch.dao.mongo.StoreDAO;
import com.sarvah.mbg.batch.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.batch.dao.mongo.UserDAO;
import com.sarvah.mbg.batch.model.PriceInfo;
import com.sarvah.mbg.batch.service.MBGCommandBase;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;

/**
 * @author shivu
 *
 */
@Component("ProductSellingPriceProcess")
public class ProductSellingPriceProcess implements MBGCommandBase {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductRatingProcess.class);

	@Value("${name:Naveen}")
	private String name;

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

	/** The store DAO **/
	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Override
	public void execute() {
		System.out
				.println("Product Selling price Service running ==== Selling price calculation");
		logger.info("Product Selling price Service running ==== Selling price calculation");
		try {
			calculateSellingPrice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculateSellingPrice() throws Exception {
		System.out.println("calculateSellingPrice() method running");
		// Taking all the products from the mongodb
		// List<Product> productList = productDAO.findAll();

		List<Product> productList = productDAO
				.findByStatus(ProductStatus.IN_BUILDONN);

		logger.info("Count of Products:  {} ", productList.size());
		int count = 0;
		for (Product product : productList) {
			List<Store> storeList = new ArrayList<>();
			List<PriceInfo> priceInfoList = new ArrayList<>();

			if (product != null) {
				count = count + 1;
				System.out.println("pId" + count + ":" + product.getId());

				Price price = product.getPrice();
				if (price != null) {

					// finding stores based on product.
					storeList = storeDAO.findByProducts(product);

					if (storeList.size() != 0 && storeList != null) {
						for (Store store : storeList) {
							if (store != null) {
								PriceInfo priceInfo = new PriceInfo();
								Double cost = null;

								// geting set of storeProductPricing info for
								// perticular store

								StoreProductPricing storeProductPricing = storeProductPricingDAO
										.findByProductIdAndStoreId(
												product.getId(), store.getId());

								if (storeProductPricing != null) {

									if (storeProductPricing.getSellingPrice() != null
											&& store.getUser() != null) {

										if (storeProductPricing.getMbgShare() != null) {

											double mbgShare = (storeProductPricing
													.getSellingPrice() * storeProductPricing
													.getMbgShare()) / 100;

											cost = storeProductPricing
													.getSellingPrice()
													+ mbgShare;

											priceInfo
													.setSelling(storeProductPricing
															.getSellingPrice()
															+ mbgShare);

											priceInfo
													.setSavings((storeProductPricing
															.getMaxRetailPrice())
															- (storeProductPricing
																	.getSellingPrice() + mbgShare));
										} else {
											cost = storeProductPricing
													.getSellingPrice();

											priceInfo
													.setSelling(storeProductPricing
															.getSellingPrice());

											priceInfo
													.setSavings(storeProductPricing
															.getMaxRetailPrice()
															- storeProductPricing
																	.getSellingPrice());
										}

										priceInfo.setCost(cost);

										priceInfo.setDealerId(store.getUser()
												.getId());

										priceInfo.setMrp(storeProductPricing
												.getMaxRetailPrice());

										if (storeProductPricing.getDiscount() != null) {
											priceInfo
													.setPctSavings(storeProductPricing
															.getDiscount());
										}
										priceInfoList.add(priceInfo);
									}
								}
							}
						}

						// passing priceInfoList to price comparator
						Collections.sort(priceInfoList, new PriceComparator());

						// taking minimum price info from priceInfoList
						if (priceInfoList.size() != 0
								&& priceInfoList.get(0) != null) {
							price.setCurrency("Rupee");
							price.setMrp(priceInfoList.get(0).getMrp());
							price.setPctSavings(priceInfoList.get(0)
									.getPctSavings());
							price.setSelling(priceInfoList.get(0).getSelling());
							price.setSavings(priceInfoList.get(0).getSavings());
							product.setPrice(price);
						}
					} else {
						// if no stores is available for this product, we are
						// setting selling price as mrp.
						price.setSelling(product.getPrice().getMrp());
						product.setPrice(price);
					}
					productDAO.save(product);
				}
			}
		}
		System.out.println("Success");
	}
}
