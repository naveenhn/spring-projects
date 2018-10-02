package com.sarvah.mbg.promotion.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.PromotionDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.catalog.service.model.PromotionCountResponse;
import com.sarvah.mbg.catalog.service.model.PromotionDetailView;
import com.sarvah.mbg.catalog.service.model.PromotionSummaryView;
import com.sarvah.mbg.catalog.service.model.PromotionSummaryViewResponse;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.marketing.PromotionType;
import com.sarvah.mbg.promotion.service.PromotionService;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;

@Service
public class PromotionServiceImpl implements PromotionService {
	private static final Logger logger = LoggerFactory
			.getLogger(PromotionServiceImpl.class);

	@Autowired
	private PromotionDAO promotionDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	@Autowired
	private BrandDAO brandDAO;

	/**
	 * Method to get all promotions
	 * 
	 * @return
	 */
	@Override
	public List<Promotion> getAllPromotions() {
		List<Promotion> promotions = new ArrayList<>();
		promotions = promotionDAO.findAll();
		return promotions;
	}

	/**
	 * Method to get Admin manage promotions
	 * 
	 * @return
	 */
	@Override
	public PromotionSummaryViewResponse getAdminManagePromotions(
			String searchValue, String status, int page, int size, Sort sort) {
		PromotionSummaryViewResponse promotionSummaryViewResponse = new PromotionSummaryViewResponse();
		List<Promotion> promotions = new ArrayList<>();
		List<PromotionSummaryView> promotionSummaryViewList = new ArrayList<>();
		Page<Promotion> pageable = null;

		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)) {
			pageable = promotionDAO.findAll(new PageRequest(page, size, sort));
			promotions.addAll(pageable.getContent());
		}
		if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(status)) {
			pageable = promotionDAO.findByNameAndActive(searchValue,
					Boolean.valueOf(status), new PageRequest(page, size, sort));
			promotions.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			pageable = promotionDAO.findByName(searchValue, new PageRequest(
					page, size, sort));
			promotions.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {

			pageable = promotionDAO.findByActive(Boolean.valueOf(status),
					new PageRequest(page, size, sort));
			promotions.addAll(pageable.getContent());
		}
		for (Promotion promotion : promotions) {
			PromotionSummaryView promotionSummaryView = new PromotionSummaryView();

			if (promotion.getProductIds() != null) {
				// Count of Products
				promotionSummaryView.setNoOfProducts(promotion.getProductIds()
						.size());
				Set<Product> products = new HashSet<Product>();
				for (String prodId : promotion.getProductIds()) {
					Product product = productDAO.findById(prodId);
					products.add(product);
				}
				promotionSummaryView.setProducts(products);
			} else if (promotion.getSubCategory() != null) {
				// Category/Subcategory
				promotionSummaryView.setSubCategory(promotion.getSubCategory());
				promotionSummaryView.setSubCategoryCount(promotion
						.getSubCategory().size());
				Set<String> categoryList = new HashSet<>();
				for (SubCategory subCategory : promotion.getSubCategory()) {
					categoryList.add(subCategory.getCategory());
				}
				promotionSummaryView.setMainCategory(categoryList);
				promotionSummaryView.setMainCategoryCount(categoryList.size());
			} else if (promotion.getBrands() != null) {
				// Brands
				promotionSummaryView.setBrands(promotion.getBrands());
				promotionSummaryView
						.setBrandCount(promotion.getBrands().size());
			}

			// Promotion Id
			promotionSummaryView.setPromoId(promotion.getId());

			// Promotion Name
			promotionSummaryView.setPromotionName(promotion.getName());

			// Start Date
			promotionSummaryView.setStartDate(promotion.getStartDate());
			// End Date
			promotionSummaryView.setEndDate(promotion.getEndDate());

			// Promo code
			promotionSummaryView.setCouponCode(promotion.getPromoCode());

			// Discount %
			promotionSummaryView.setDiscountPercentage(promotion.getDiscount());

			// Status
			if (promotion.isActive()) {
				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					promotionSummaryView.setStatus("ACTIVE");
					promotion.setActive(true);
				} else {
					logger.info("Promotion expired");
					promotionSummaryView.setStatus("INACTIVE");
					promotion.setActive(false);
				}
			} else {
				promotionSummaryView.setStatus("INACTIVE");
				promotion.setActive(false);
			}
			promotionSummaryViewList.add(promotionSummaryView);
			promotionDAO.save(promotion);
		}
		promotionSummaryViewResponse
				.setPromotionSummaryView(promotionSummaryViewList);
		promotionSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		promotionSummaryViewResponse.setTotalElements(pageable
				.getTotalElements());
		promotionSummaryViewResponse.setNumber(pageable.getNumber());
		promotionSummaryViewResponse.setSize(pageable.getSize());
		return promotionSummaryViewResponse;
	}

	/**
	 * Method to get available promotions
	 * 
	 * @return
	 * @throws ParseException
	 */
	@Override
	public List<Promotion> getAllCurrentPromotions() throws ParseException {
		List<Promotion> promotionList = promotionDAO.findAll();
		List<Promotion> promotions = new ArrayList<>();

		for (Promotion promotion : promotionList) {
			if (promotion.isActive()) {

				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					promotions.add(promotion);
				} else {
					logger.info("Inactive Promotion(Promotion expired)");
				}
			} else {
				logger.info("Inactive Promotion");
			}
		}
		return promotions;
	}

	/**
	 * Method to get particular promotion
	 * 
	 * @param promid
	 * @return
	 */
	@Override
	public PromotionDetailView getPromotionById(String promid) {
		PromotionDetailView promotionViewDetails = new PromotionDetailView();

		Promotion promotion = promotionDAO.findOne(promid);

		if (promotion != null) {

			if (promotion.getProductIds() != null) {
				promotionViewDetails.setNoOfProducts(promotion.getProductIds()
						.size());
				Set<Product> products = new HashSet<Product>();
				for (String prodId : promotion.getProductIds()) {
					Product product = productDAO.findById(prodId);
					products.add(product);
				}
				promotionViewDetails.setProducts(products);
			}
			if (promotion.getSubCategory() != null) {
				// Category/Subcategory
				promotionViewDetails.setSubCategory(promotion.getSubCategory());
				promotionViewDetails.setSubCategoryCount(promotion
						.getSubCategory().size());
				Set<String> categoryList = new HashSet<>();
				for (SubCategory subCategory : promotion.getSubCategory()) {
					categoryList.add(subCategory.getCategory());
				}
				promotionViewDetails.setMainCategory(categoryList);
				promotionViewDetails.setMainCategoryCount(categoryList.size());
			} else if (promotion.getBrands() != null) {
				// Brands
				promotionViewDetails.setBrands(promotion.getBrands());
				promotionViewDetails
						.setBrandCount(promotion.getBrands().size());
			}

			// Promotion Name
			promotionViewDetails.setPromotionName(promotion.getName());

			// Start Date
			promotionViewDetails.setStartDate(promotion.getStartDate());
			// End Date
			promotionViewDetails.setEndDate(promotion.getEndDate());

			// Discount Percentage
			promotionViewDetails.setDiscountPercentage(promotion.getDiscount());

			// Coupon Code
			promotionViewDetails.setCouponCode(promotion.getPromoCode());

			// Status

			if (promotion.isActive()) {

				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					promotionViewDetails.setStatus("ACTIVE");
					promotion.setActive(true);
				} else {
					logger.info("Promotion is inactive");
				}
			} else {
				promotionViewDetails.setStatus("INACTIVE");
			}

		}

		return promotionViewDetails;
	}

	/**
	 * Method to get Admin promotion View details
	 * 
	 * @param promid
	 * @return
	 */
	@Override
	public PromotionDetailView getPromotionAdminViewDetails(String promid) {
		PromotionDetailView promotionViewDetails = new PromotionDetailView();

		Promotion promotion = promotionDAO.findOne(promid);

		if (promotion != null) {

			if (promotion.getProductIds() != null) {
				promotionViewDetails.setNoOfProducts(promotion.getProductIds()
						.size());
				Set<Product> products = new HashSet<Product>();
				for (String prodId : promotion.getProductIds()) {
					Product product = productDAO.findById(prodId);
					products.add(product);
				}
				promotionViewDetails.setProducts(products);
			}
			if (promotion.getSubCategory() != null) {
				// Category/Subcategory
				promotionViewDetails.setSubCategory(promotion.getSubCategory());
				promotionViewDetails.setSubCategoryCount(promotion
						.getSubCategory().size());
				Set<String> categoryList = new HashSet<>();
				for (SubCategory subCategory : promotion.getSubCategory()) {
					categoryList.add(subCategory.getCategory());
				}
				promotionViewDetails.setMainCategory(categoryList);
				promotionViewDetails.setMainCategoryCount(categoryList.size());
			} else if (promotion.getBrands() != null) {
				// Brands
				promotionViewDetails.setBrands(promotion.getBrands());
				promotionViewDetails
						.setBrandCount(promotion.getBrands().size());
			}

			// Promotion Name
			promotionViewDetails.setPromotionName(promotion.getName());

			// Start Date
			promotionViewDetails.setStartDate(promotion.getStartDate());
			// End Date
			promotionViewDetails.setEndDate(promotion.getEndDate());

			// Discount Percentage
			promotionViewDetails.setDiscountPercentage(promotion.getDiscount());

			// Coupon Code
			promotionViewDetails.setCouponCode(promotion.getPromoCode());

			// Status
			if (promotion.isActive()) {

				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					promotionViewDetails.setStatus("ACTIVE");
					promotion.setActive(true);
				} else {
					logger.info("Promotion is inactive");
				}
			} else {
				promotionViewDetails.setStatus("INACTIVE");
			}
		}
		return promotionViewDetails;
	}

	/**
	 * Method for get promotion for the particular product.
	 * 
	 * @param prodId
	 * @param promotionSearchRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */

	@Override
	public List<Promotion> getPromotionForProduct(String prodId, String pName,
			String pType, String discount, String sDate, String eDate)
			throws Exception {
		List<Promotion> promotion = null;
		if (StringUtils.isNotBlank(prodId)) {
			logger.info("finding promotion for product");
			if (StringUtils.isNotBlank(pName)) {
				promotion = promotionDAO.findByName(pName);
			} else if (StringUtils.isNotBlank(pType)) {
				promotion = promotionDAO.findByType(PromotionType
						.valueOf(pType));
			} else if (StringUtils.isNotBlank(discount)) {
				promotion = promotionDAO.findByDiscount(Double
						.valueOf(discount));
			} else if (StringUtils.isNotBlank(sDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date startDate = formatter.parse(sDate);
				promotion = promotionDAO.findByStartDate(startDate);
			} else if (StringUtils.isNotBlank(eDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date endDate = formatter.parse(eDate);
				promotion = promotionDAO.findByEndDate(endDate);
			} else {
				Set<String> productIds = new HashSet<>();
				productIds.add(prodId);
				promotion = promotionDAO.findByProductIdsIn(productIds);
			}
		} else {
			logger.info("Promotion id is null");
			throw new Exception("Promotion id should not be null");
		}
		return promotion;
	}

	/**
	 * Method to get count of promotions for a product
	 * 
	 * @param pid
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@Override
	public long getPromotionCount(String proId, String name, String type,
			String discount, String sDate, String eDate) throws Exception {

		Long count = null;
		if (StringUtils.isNotBlank(proId)) {
			logger.info("finding promotion count");
			if (StringUtils.isNotBlank(name)) {
				count = promotionDAO.countByName(name);
			} else if (StringUtils.isNotBlank(type)) {
				count = promotionDAO.countByType(PromotionType.valueOf(type));
			} else if (StringUtils.isNotBlank(discount)) {
				count = promotionDAO.countByDiscount(Double.valueOf(discount));
			} else if (StringUtils.isNotBlank(sDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date startDate = formatter.parse(sDate);
				count = promotionDAO.countByStartDate(startDate);
			} else if (StringUtils.isNotBlank(eDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date endDate = formatter.parse(eDate);
				count = promotionDAO.countByEndDate(endDate);
			} else {
				Set<String> productIds = new HashSet<>();
				productIds.add(proId);
				count = promotionDAO.countByProductIdsIn(productIds);
			}
		} else {
			logger.info("Promotion id should not be null");
			throw new Exception("Promotion id should not be null");
		}
		return count;
	}

	/**
	 * method for create promocode
	 * 
	 * @param promocodeCreateRequestParam
	 * @return
	 */
	@Override
	public Promotion createPromoCode(Set<String> subCategories,
			Set<String> productIds, String promoCode, String startDate,
			String endDate, String discount, String desc, String promotionType,
			String promotionName, String status, Set<String> brands)
			throws Exception {
		Promotion promotion = new Promotion();

		if (subCategories != null) {
			Set<SubCategory> subcategoriesList = new HashSet<>();
			for (String subCategoryId : subCategories) {
				if (subCategoryId != null) {
					SubCategory subCategory = subCategoryDAO
							.findOne(subCategoryId);
					if (subCategory != null) {
						subcategoriesList.add(subCategory);
					}
				}
			}
			promotion.setSubCategory(subcategoriesList);
			if (StringUtils.isNotBlank(promoCode)
					&& StringUtils.isNotBlank(discount)) {

				promotion.setPromoCode(promoCode);
				promotion.setDiscount(Double.valueOf(discount));
			} else {
				logger.info("promoCode And discount can not be null");
				throw new Exception("promoCode And discount can not be null");
			}

			if (StringUtils.isNotBlank(desc)) {
				Description desc1 = new Description();
				desc1.setLang("eng");
				desc1.setVal(desc);
				promotion.setDesc(desc1);

			} else {
				logger.info("description can not be null");
			}

			if (StringUtils.isNotBlank(promotionName)) {
				promotion.setName(promotionName);
			} else {
				logger.info("PromotionName null");
			}

			if (StringUtils.isNotBlank(promotionType)) {
				promotion.setType(PromotionType.valueOf(promotionType));
			} else {
				logger.info("PromotionType null");
			}
			if (StringUtils.isNotBlank(startDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date sDate = formatter.parse(startDate);
				promotion.setStartDate(sDate);
			} else {
				logger.info("Start Date null");
			}

			if (StringUtils.isNotBlank(endDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date eDate = formatter.parse(endDate);
				promotion.setEndDate(eDate);
			} else {
				logger.info("End Date null");
			}
			if (StringUtils.isNotBlank(status)) {
				if (status.equalsIgnoreCase("active")) {
					promotion.setActive(true);
				} else {
					promotion.setActive(false);
				}
			} else {
				logger.info("status null");
			}

			promotionDAO.insert(promotion);

		} else if (brands != null) {
			logger.info("subcategories null");

			Set<ProductBrand> brandList = new HashSet<>();
			for (String brandId : brands) {
				ProductBrand brand = brandDAO.findOne(brandId);
				if (brand != null) {
					brandList.add(brand);
				}

			}
			promotion.setBrands(brandList);

			if (StringUtils.isNotBlank(promoCode)
					&& StringUtils.isNotBlank(discount)) {

				promotion.setPromoCode(promoCode);
				promotion.setDiscount(Double.valueOf(discount));
			} else {
				logger.info("promoCode And discount can not be null");
				throw new Exception("promoCode And discount can not be null");
			}

			if (StringUtils.isNotBlank(desc)) {
				Description desc1 = new Description();
				desc1.setLang("eng");
				desc1.setVal(desc);
				promotion.setDesc(desc1);

			} else {
				logger.info("description can not be null");
			}

			if (StringUtils.isNotBlank(promotionName)) {
				promotion.setName(promotionName);
			} else {
				logger.info("PromotionName null");
			}

			if (StringUtils.isNotBlank(promotionType)) {
				promotion.setType(PromotionType.valueOf(promotionType));
			} else {
				logger.info("PromotionType null");
			}
			if (StringUtils.isNotBlank(startDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date sDate = formatter.parse(startDate);
				promotion.setStartDate(sDate);
			} else {
				logger.info("Start Date null");
			}

			if (StringUtils.isNotBlank(endDate)) {
				DateFormat formatter = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date eDate = formatter.parse(endDate);
				promotion.setEndDate(eDate);
			} else {
				logger.info("End Date null");
			}
			if (StringUtils.isNotBlank(status)) {
				if (status.equalsIgnoreCase("active")) {
					promotion.setActive(true);
				} else {
					promotion.setActive(false);
				}
			} else {
				logger.info("status null");
			}

			promotionDAO.insert(promotion);

		} else {
			logger.info("subcategories null");
			logger.info("brands null");
			if (productIds != null) {
				promotion.setProductIds(productIds);

				if (StringUtils.isNotBlank(promoCode)
						&& StringUtils.isNotBlank(discount)) {

					promotion.setPromoCode(promoCode);
					promotion.setDiscount(Double.valueOf(discount));
				} else {
					logger.info("promoCode And discount can not be null");
					throw new Exception(
							"promoCode And discount can not be null");
				}

				if (StringUtils.isNotBlank(desc)) {
					Description desc1 = new Description();
					desc1.setLang("eng");
					desc1.setVal(desc);
					promotion.setDesc(desc1);

				} else {
					logger.info("description can not be null");
				}

				if (StringUtils.isNotBlank(promotionName)) {
					promotion.setName(promotionName);
				} else {
					logger.info("PromotionName null");
				}

				if (StringUtils.isNotBlank(promotionType)) {
					promotion.setType(PromotionType.valueOf(promotionType));
				} else {
					logger.info("PromotionType null");
				}
				if (StringUtils.isNotBlank(startDate)) {
					DateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");
					Date sDate = formatter.parse(startDate);
					promotion.setStartDate(sDate);
				} else {
					logger.info("Start Date null");
				}

				if (StringUtils.isNotBlank(endDate)) {
					DateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");
					Date eDate = formatter.parse(endDate);
					promotion.setEndDate(eDate);
				} else {
					logger.info("End Date null");
				}
				if (StringUtils.isNotBlank(status)) {
					if (status.equalsIgnoreCase("active")) {
						promotion.setActive(true);
					} else {
						promotion.setActive(false);
					}
				} else {
					logger.info("status null");
				}
				promotionDAO.insert(promotion);
			} else {
				logger.info("Subcategories or ProductIds can not be null ");
				throw new Exception(
						" Subcategories or ProductIds can not be null");
			}

		}

		return promotion;
	}

	/**
	 * method for update promocode based on promocodeId.
	 * 
	 * @param promid
	 * @param promocodeUpdateRequestParam
	 * @return
	 */
	@Override
	public Promotion updatePromoCode(String promid, Set<String> subCategories,
			Set<String> productIds, String promoCode, String startDate,
			String endDate, String discount, String desc, String promotionType,
			String promotionName, String status, Set<String> brands)
			throws Exception {
		Promotion promotion = null;
		if (StringUtils.isNotBlank(promid)) {
			promotion = promotionDAO.findOne(promid);
			if (promotion != null) {

				if (productIds != null && !(productIds.isEmpty())) {
					if (promotion.getProductIds() != null) {
						promotion.setProductIds(productIds);
					} else {
						logger.info("promotion doesn't have any products");
						throw new Exception(
								" promotion doesn't have any products");
					}
				}
				if (subCategories != null) {
					if (promotion.getSubCategory() != null) {
						Set<SubCategory> subcategoriesList = new HashSet<>();
						for (String subCategoryId : subCategories) {
							if (subCategoryId != null) {
								SubCategory subCategory = subCategoryDAO
										.findOne(subCategoryId);
								if (subCategory != null) {
									subcategoriesList.add(subCategory);
								}
							}
						}
						promotion.setSubCategory(subcategoriesList);
					} else {
						logger.info("promotion doesn't have any subcategories");
						throw new Exception(
								" promotion doesn't have any subcategories");
					}

				}

				if (brands != null) {
					if (promotion.getBrands() != null) {
						Set<ProductBrand> brandList = new HashSet<>();
						for (String brandId : brands) {
							ProductBrand brand = brandDAO.findOne(brandId);
							if (brand != null) {
								brandList.add(brand);
							}

						}
						promotion.setBrands(brandList);
					} else {
						logger.info("promotion doesn't have any Brands");
						throw new Exception(
								" promotion doesn't have any Brands");
					}
				}

				if (StringUtils.isNotBlank(status)
						&& StringUtils.isNotBlank(endDate)) {

					DateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");

					Date tDate = new Date();
					Date eDate = formatter.parse(endDate);

					String date1 = formatter.format(tDate);
					String date2 = formatter.format(eDate);

					if (date1.compareTo(date2) < 0) {
						if (status.equalsIgnoreCase("active")) {
							promotion.setActive(true);
						}
					} else {
						logger.info("End date expired");
						if (status.equalsIgnoreCase("Inactive")) {
							promotion.setActive(false);
						}
					}
				} else if (StringUtils.isNotBlank(endDate)) {
					DateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");
					Date tDate = new Date();
					Date eDate = formatter.parse(endDate);
					promotion.setEndDate(eDate);

					String date1 = formatter.format(tDate);
					String date2 = formatter.format(eDate);

					if (date1.compareTo(date2) < 0) {
						promotion.setActive(true);
					} else {
						promotion.setActive(false);
					}

				}
				if (StringUtils.isNotBlank(startDate)) {
					DateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");
					Date sDate = formatter.parse(startDate);
					promotion.setStartDate(sDate);
				}

				if (StringUtils.isNotBlank(promotionName)) {
					promotion.setName(promotionName);
				}

				if (StringUtils.isNotBlank(promotionType)) {
					promotion.setType(PromotionType.valueOf(promotionType));
				}
				if (StringUtils.isNotBlank(desc)) {
					Description desc1 = new Description();
					desc1.setLang("eng");
					desc1.setVal(desc);
					promotion.setDesc(desc1);
				}
				if (StringUtils.isNotBlank(promoCode)) {
					promotion.setPromoCode(promoCode);
				}
				if (StringUtils.isNotBlank(discount)) {
					promotion.setDiscount(Double.valueOf(discount));
				}
			} else {
				logger.info("Promotion doesn't exists in database ");
				throw new Exception(" Promotion doesn't exists in database");
			}
		} else {
			logger.info("Promotion id can not be null ");
			throw new Exception(" Promotion id can not be null");
		}
		promotionDAO.save(promotion);
		return promotion;
	}

	/**
	 * method for delete promotion based on promotionId.
	 * 
	 * @param promid
	 * @return
	 */
	@Override
	public Promotion deletePromotion(String promid) throws Exception {
		Promotion promotion = null;
		if (StringUtils.isNotBlank(promid)) {
			promotion = promotionDAO.findOne(promid);
			if (promotion != null) {
				logger.info("Deleting a promotion");
				promotionDAO.delete(promid);
			} else {
				logger.info("Promotion doesn't exists in database ");
				throw new Exception(" Promotion doesn't exists in database");
			}
		} else {
			logger.info("Promotion id can not be null ");
			throw new Exception(" Promotion id can not be null");
		}
		return promotion;
	}

	/**
	 * method for get promotion count
	 * 
	 * @return
	 */
	@Override
	public PromotionCountResponse getAllPromotioscount() {
		PromotionCountResponse promotionCountResponse = new PromotionCountResponse();
		Long promoCount = 0l;
		Integer activePromoCount = 0;
		Integer inactivePromoCount = 0;
		List<Promotion> promotions = promotionDAO.findAll();
		for (Promotion promotion : promotions) {
			// Status
			if (promotion.isActive()) {
				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					activePromoCount++;
				} else {
					inactivePromoCount++;
					logger.info("Promotion expired");
				}
			} else {
				inactivePromoCount++;
			}
		}
		promoCount = (long) (activePromoCount + inactivePromoCount);
		promotionCountResponse.setPromoCount(promoCount);
		promotionCountResponse.setActivePromoCount(activePromoCount);
		promotionCountResponse.setInactivePromoCount(inactivePromoCount);
		return promotionCountResponse;
	}
}
