package com.sarvah.mbg.promotion.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.service.model.PromotionDetailView;
import com.sarvah.mbg.catalog.service.model.PromotionSummaryViewResponse;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;

public interface PromotionService {

	/**
	 * Method to get all promotions
	 * 
	 * @return
	 */
	List<Promotion> getAllPromotions();

	/**
	 * Method to get Admin manage promotions
	 * 
	 * @return
	 */
	PromotionSummaryViewResponse getAdminManagePromotions(String searchValue,
			String status, int page, int size, Sort sort);

	/**
	 * Method to get available promotions
	 * 
	 * @return
	 * @throws ParseException
	 */
	List<Promotion> getAllCurrentPromotions() throws ParseException;

	/**
	 * Method to get particular promotion
	 * 
	 * @param promid
	 * @return
	 */
	PromotionDetailView getPromotionById(String promid);

	/**
	 * Method to get Admin Promotion View details
	 * 
	 * @param promid
	 * @return
	 */
	PromotionDetailView getPromotionAdminViewDetails(String promid);

	/**
	 * Method for get promotion for the particular product.
	 * 
	 * @param prodId
	 * @param promotionSearchRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	List<Promotion> getPromotionForProduct(String prodId, String pName,
			String pType, String discount, String sDate, String eDate)
			throws ParseException, Exception;

	/**
	 * Method to get count of promotions for a product
	 * 
	 * @param pid
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	long getPromotionCount(String pid, String name, String type,
			String discount, String sDate, String eDate) throws ParseException,
			Exception;

	/**
	 * method for create promocode
	 * 
	 * @param promocodeCreateRequestParam
	 * @return
	 */

	Promotion createPromoCode(Set<String> subCategories,
			Set<String> productIds, String promoCode, String startDate,
			String endDate, String discount, String desc, String promotionType,
			String promotionName, String status, Set<String> brands)
			throws Exception;

	/**
	 * method for update promocode based on promocodeId.
	 * 
	 * @param promid
	 * @param promocodeUpdateRequestParam
	 * @return
	 */
	Promotion updatePromoCode(String promid, Set<String> subCategories,
			Set<String> productIds, String promoCode, String startDate,
			String endDate, String discount, String desc, String promotionType,
			String promotionName, String status, Set<String> brands)
			throws Exception;

	/**
	 * method for delete promotion based on promotionId.
	 * 
	 * @param promid
	 * @return
	 */

	Promotion deletePromotion(String promid) throws Exception;

	/**
	 * method for get promotion count
	 * 
	 * @return
	 */
	com.sarvah.mbg.catalog.service.model.PromotionCountResponse getAllPromotioscount();
}
