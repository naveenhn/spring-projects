/**
 * 
 */
package com.sarvah.mbg.store.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.UITemplate;
import com.sarvah.mbg.domain.mongo.store.Shipping;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.store.dao.mongo.StoreProductDAO;
import com.sarvah.mbg.store.dao.mongo.UITemplateDAO;
import com.sarvah.mbg.store.service.StoreService;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreSubcategoryDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author sumanth
 *
 */
@Service
public class StoreServiceImpl implements StoreService {
	private static final Logger logger = LoggerFactory
			.getLogger(StoreServiceImpl.class);

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private StoreProductDAO productDAO;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Autowired
	private UITemplateDAO templateDAO;

	@Autowired
	private StoreSubcategoryDAO subCategoryDAO;

	@Autowired
	private BrandDAO brandDAO;

	/**
	 * method for get store based on dealerId and store name.
	 * 
	 * @param dealerId
	 * @param storename
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Store> findStore(String dealerId, String storename, int page,
			int size, Sort sort) throws Exception {
		List<Store> storeList = new ArrayList<Store>();
		Store store = null;
		if (StringUtils.isNotBlank(dealerId)) {
			User users = userDAO.findOne(dealerId);
			if (users != null) {

				if (StringUtils.isNotBlank(storename)) {
					store = storeDAO.findByStorename(storename);
					storeList.add(store);
				} else {
					storeList = storeDAO.findByUserId(dealerId,
							new PageRequest(page, size, sort)).getContent();
				}
			} else {
				store = null;
				throw new Exception("Store null");
			}
		} else {
			store = null;
			throw new Exception("DealerId null");
		}
		return storeList;
	}

	/**
	 * method for get store count based on dealerId.
	 * 
	 * @param dealerId
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public int count(String dealerId) throws Exception {
		int count;
		if (StringUtils.isNotBlank(dealerId)) {
			User dealer = userDAO.findOne(dealerId);
			if (dealer != null) {
				count = storeDAO.countByUserId(dealerId);
			} else {
				throw new Exception("Dealer doesn't exists");
			}
		} else {
			count = 0;
			throw new Exception("DealerId null");
		}
		return count;
	}

	/**
	 * Method to create new store
	 * 
	 * @param dealerId
	 * @param storeName
	 * @param desc
	 * @param templateId
	 * @param productId
	 * @param mrp
	 * @param selling_Price
	 * @param listing_Status
	 * @param fulfilled_By
	 * @param national_Shipping_Charge
	 * @param zonal_Shipping_Charge
	 * @param local_Shipping_Charge
	 * @param stock_Count
	 * @param tax
	 * @return
	 * @throws Exception
	 */
	@Override
	public Store createStore(String dealerId, String storeName, String desc,
			String templateId, List<String> productIds, String mrp,
			String sellingPrice, String listingStatus, String fulfilledBy,
			String stockCount, String minQtyBuy, String tax, String mbgShare,
			String discount, String storeDelivery,
			String nationalShippingCharge, String nationalDelivery,
			String minNationalProcurementSLA, String maxNationalProcurementSLA,
			String zonalShippingCharge, String zonalDelivery,
			String minZonalProcurementSLA, String maxZonalProcurementSLA,
			String localShippingCharge, String localDelivery,
			String minLocalProcurementSLA, String maxLocalProcurementSLA)
			throws Exception {

		Store store = new Store();
		UITemplate uiTemplate = null;
		List<Product> products = new ArrayList<>();

		if (StringUtils.isNotBlank(dealerId)) {
			User user = userDAO.findOne(dealerId);

			if (user != null) {
				if (StringUtils.isNotBlank(storeName)) {
					store.setStorename(storeName);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description descr = new Description();
					descr.setLang("Eng");
					descr.setVal(desc);
					store.setDesc(descr);
				}
				if (StringUtils.isNotBlank(templateId)) {
					uiTemplate = templateDAO.findOne(templateId);
					if (uiTemplate != null) {
						store.setTemplate(uiTemplate);
					} else {
						logger.info("Template doesn't exist");
						throw new Exception("Template doesn't exist");
					}
				}

				if (productIds.size() != 0) {
					for (String productID : productIds) {

						Product product = productDAO.findOne(productID);
						if (product != null) {
							products.add(product);
						} else {
							throw new Exception("product should not null");
						}
					}
					store.setProducts(products);
				} else {
					logger.info("Product not available");
					throw new Exception("Product not available");
				}

				storeDAO.save(store);

				for (String pid : productIds) {
					Product product = productDAO.findOne(pid);
					if (product != null) {
						StoreProductPricing storeProductPricing = new StoreProductPricing(
								store.getId(), pid, product.getSkuId());

						storeProductPricing.setProductId(pid);
						storeProductPricing.setMaxRetailPrice(product
								.getPrice().getMrp());
						storeProductPricing.setSellingPrice(Double
								.parseDouble(sellingPrice));
						storeProductPricing.setListingStatus(listingStatus);
						storeProductPricing.setFulfilledBy(product.getBrand()
								.getName());
						storeProductPricing.setStockCount(Integer
								.parseInt(stockCount));
						storeProductPricing.setMinQuantityBuy(Integer
								.parseInt(minQtyBuy));
						storeProductPricing.setTax(Double.parseDouble(tax));
						storeProductPricing.setMbgShare(Double
								.parseDouble(mbgShare));
						storeProductPricing.setDiscount(Double
								.parseDouble(discount));
						if (StringUtils.isNotBlank(storeDelivery)
								&& storeDelivery.equalsIgnoreCase("True")) {
							storeProductPricing.setStoreDelivery(true);
						} else {
							storeProductPricing.setStoreDelivery(false);
						}

						Shipping shipping = new Shipping();

						if (StringUtils.isNotBlank(localShippingCharge)) {
							shipping.setLocalShippingChrg(Double
									.parseDouble(localShippingCharge));
						}
						if (StringUtils.isNotBlank(localDelivery)
								&& localDelivery.equalsIgnoreCase("True")) {
							shipping.setLocalDelivery(true);
						} else {
							shipping.setLocalDelivery(false);
						}
						if (StringUtils.isNotBlank(minLocalProcurementSLA)
								&& StringUtils
										.isNotBlank(maxLocalProcurementSLA)) {
							shipping.setMinLocalProcurementSLA(Integer
									.parseInt(minLocalProcurementSLA));
							shipping.setMaxLocalProcurementSLA(Integer
									.parseInt(maxLocalProcurementSLA));
						}

						if (StringUtils.isNotBlank(zonalShippingCharge)) {
							shipping.setZonalShippingChrg(Double
									.parseDouble(zonalShippingCharge));
						}
						if (StringUtils.isNotBlank(zonalDelivery)
								&& zonalDelivery.equalsIgnoreCase("True")) {
							shipping.setZonalDelivery(true);
						} else {
							shipping.setZonalDelivery(false);
						}
						if (StringUtils.isNotBlank(minZonalProcurementSLA)
								&& StringUtils
										.isNotBlank(maxZonalProcurementSLA)) {
							shipping.setMinZonalProcurementSLA(Integer
									.parseInt(minZonalProcurementSLA));
							shipping.setMaxZonalProcurementSLA(Integer
									.parseInt(maxZonalProcurementSLA));
						}

						if (StringUtils.isNotBlank(nationalDelivery)) {
							shipping.setNationalShippingChrg(Double
									.parseDouble(nationalShippingCharge));
						}
						if (StringUtils.isNotBlank(nationalDelivery)
								&& nationalDelivery.equalsIgnoreCase("True")) {
							shipping.setNationalDelivery(true);
						} else {
							shipping.setNationalDelivery(false);
						}
						if (StringUtils.isNotBlank(minNationalProcurementSLA)
								&& StringUtils
										.isNotBlank(maxNationalProcurementSLA)) {
							shipping.setMinNationalProcurementSLA(Integer
									.parseInt(minNationalProcurementSLA));
							shipping.setMaxNationalProcurementSLA(Integer
									.parseInt(maxNationalProcurementSLA));
						}

						storeProductPricing.setShipping(shipping);

						storeProductPricingDAO.save(storeProductPricing);
					}
				}
			}
		}
		return store;
	}

	/**
	 * Method to update store information
	 * 
	 * @param dealerId
	 * @param storeId
	 * @param storeUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Store updateStoreInformation(String dealerId, String storeId,
			String storeName, String subCategory, String brands) {
		Store store = null;
		if (StringUtils.isNotBlank(dealerId)) {
			User dealer = userDAO.findOne(dealerId);

			if (dealer != null) {

				if (StringUtils.isNotBlank(storeId)) {
					store = storeDAO.findByIdAndUser(storeId, dealer);

					if (store != null) {

						if (StringUtils.isNotBlank(storeName)) {
							store.setStorename(storeName);
						}

						if (StringUtils.isNotBlank(subCategory)) {
							Set<SubCategory> subCategories = null;
							Set<SubCategory> subCategories1 = new HashSet<>();
							String[] subCategoriesName = subCategory.split(",");
							for (String subCategoryName : subCategoriesName) {
								subCategories = subCategoryDAO
										.findByName(subCategoryName);
								if (subCategories != null
										&& subCategories.size() != 0) {
									subCategories1.addAll(subCategories);
								}
							}
							if (subCategories1.size() != 0) {
								store.setSubcategories(subCategories1);
							}
						}

						if (StringUtils.isNotBlank(brands)) {
							Set<ProductBrand> brandSet = new HashSet<>();
							String[] brandIds = brands.split(",");
							for (String brandId : brandIds) {
								ProductBrand ProductBrand = brandDAO
										.findOne(brandId);
								if (ProductBrand != null) {
									brandSet.add(ProductBrand);
								}
							}
							if (brandSet.size() != 0) {
								store.setBrands(brandSet);
							}
						}

					}
				}
			}
		}
		storeDAO.save(store);
		return store;
	}

	/**
	 * Method to update store product pricing.
	 * 
	 * @param dealerId
	 * @param storeId
	 * @param productId
	 * @param storeUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public StoreProductPricing updateStoreProductInformation(String dealerId,
			String productId, String storeName, String desc, String templateId,
			String sellingPrice, String listingStatus, String fulfilledBy,
			String nationalShippingCharge, String zonalShippingCharge,
			String localShippingCharge, String minNationalProcurementSLA,
			String maxNationalProcurementSLA, String minZonalProcurementSLA,
			String maxZonalProcurementSLA, String minLocalPocurementSLA,
			String maxLocalPocurementSLA, String minQuantityBuy,
			String stockCount, String tax, String mbgShare, String localRegion,
			String zonalRegion, String nationRegion, String discount,
			String willYouDeliver) throws Exception {
		Store store = null;
		User dealer = null;
		StoreProductPricing storeProductPricing = null;

		if (StringUtils.isNotBlank(dealerId)) {
			dealer = userDAO.findById(dealerId);
		}
		if (dealer != null) {
			store = storeDAO.findByUser(dealer);
		}
		if (store != null) {
			if (StringUtils.isNotBlank(storeName)) {
				store.setStorename(storeName);
			}
			if (StringUtils.isNotBlank(desc)) {
				Description descr = new Description();
				descr.setLang("Eng");
				descr.setVal(desc);
				store.setDesc(descr);
			}
			if (StringUtils.isNotBlank(templateId)) {
				UITemplate template = templateDAO.findOne(templateId);
				store.setTemplate(template);
			}

			storeProductPricing = storeProductPricingDAO
					.findByProductIdAndStoreId(productId, store.getId());
			if (storeProductPricing != null) {

				if (storeProductPricing.getProductId().equalsIgnoreCase(
						productId)) {

					Product product = productDAO.findOne(productId);
					storeProductPricing.setProductId(productId);
					storeProductPricing.setMaxRetailPrice(product.getPrice()
							.getMrp());
					if (StringUtils.isNotBlank(sellingPrice)) {
						storeProductPricing.setSellingPrice(Double
								.parseDouble(sellingPrice));
					}
					storeProductPricing.setListingStatus(listingStatus);

					if (StringUtils.isNotBlank(fulfilledBy)) {
						if (fulfilledBy.equalsIgnoreCase("Yes")) {
							storeProductPricing.setFulfilledBy(store.getUser()
									.getFullName());
						} else if (fulfilledBy.equalsIgnoreCase("No")) {
							storeProductPricing.setFulfilledBy("Buildonn");
						}

					}
					Shipping shipping = storeProductPricing.getShipping();
					if (StringUtils.isNotBlank(nationalShippingCharge)) {
						shipping.setNationalShippingChrg(Double
								.parseDouble(nationalShippingCharge));
					}
					if (StringUtils.isNotBlank(zonalShippingCharge)) {
						shipping.setZonalShippingChrg(Double
								.parseDouble(zonalShippingCharge));
					}
					if (StringUtils.isNotBlank(localShippingCharge)) {
						shipping.setLocalShippingChrg(Double
								.parseDouble(localShippingCharge));
					}
					if (StringUtils.isNotBlank(minNationalProcurementSLA)) {
						shipping.setMinNationalProcurementSLA(Integer
								.parseInt(minNationalProcurementSLA));
					}
					if (StringUtils.isNotBlank(maxNationalProcurementSLA)) {
						shipping.setMaxNationalProcurementSLA(Integer
								.parseInt(maxNationalProcurementSLA));
					}
					if (StringUtils.isNotBlank(minZonalProcurementSLA)) {
						shipping.setMinZonalProcurementSLA(Integer
								.parseInt(minZonalProcurementSLA));
					}
					if (StringUtils.isNotBlank(maxZonalProcurementSLA)) {
						shipping.setMaxZonalProcurementSLA(Integer
								.parseInt(maxZonalProcurementSLA));
					}
					if (StringUtils.isNotBlank(minLocalPocurementSLA)) {
						shipping.setMinLocalProcurementSLA(Integer
								.parseInt(minLocalPocurementSLA));
					}
					if (StringUtils.isNotBlank(maxLocalPocurementSLA)) {
						shipping.setMaxLocalProcurementSLA(Integer
								.parseInt(maxLocalPocurementSLA));
					}
					if (StringUtils.isNotBlank(minQuantityBuy)) {
						storeProductPricing.setMinQuantityBuy(Integer
								.parseInt(minQuantityBuy));
					}
					if (StringUtils.isNotBlank(stockCount)) {
						storeProductPricing.setStockCount(Integer
								.parseInt(stockCount));
					}
					if (StringUtils.isNotBlank(tax)) {
						storeProductPricing.setTax(Double.parseDouble(tax));
					}
					if (StringUtils.isNotBlank(mbgShare)) {
						storeProductPricing.setMbgShare(Double
								.parseDouble(mbgShare));
					}

					if (StringUtils.isNotBlank(localRegion)
							&& localRegion.equalsIgnoreCase("True")) {
						shipping.setLocalDelivery(true);
					} else {
						shipping.setLocalDelivery(false);
					}

					if (StringUtils.isNotBlank(zonalRegion)
							&& zonalRegion.equalsIgnoreCase("True")) {
						shipping.setZonalDelivery(true);
					} else {
						shipping.setZonalDelivery(false);
					}

					if (StringUtils.isNotBlank(nationRegion)
							&& nationRegion.equalsIgnoreCase("True")) {
						shipping.setNationalDelivery(true);
					} else {
						shipping.setNationalDelivery(false);
					}
					if (StringUtils.isNotBlank(discount)) {
						storeProductPricing.setDiscount(Double
								.parseDouble(discount));
					}
					if (StringUtils.isNotBlank(willYouDeliver)
							&& willYouDeliver.equalsIgnoreCase("True")) {
						storeProductPricing.setStoreDelivery(true);
					} else {
						storeProductPricing.setStoreDelivery(false);
					}

					storeProductPricing.setShipping(shipping);
					storeProductPricingDAO.save(storeProductPricing);
				}
			} else {
				throw new Exception(
						"This product is not yet linked to your store, first click on link then save the changes");
			}
			storeDAO.save(store);
		} else {
			throw new Exception("Store is null");
		}
		return storeProductPricing;
	}
}
