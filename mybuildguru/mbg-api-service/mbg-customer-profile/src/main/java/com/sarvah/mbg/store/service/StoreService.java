/**
 * 
 */
package com.sarvah.mbg.store.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;

/**
 * @author sumanth
 *
 */
public interface StoreService {

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
	List<Store> findStore(String dealerId, String storename, int page,
			int size, Sort sort) throws Exception;

	/**
	 * method for get store count based on dealerId.
	 * 
	 * @param dealerId
	 * @return
	 * @throws MBGAppException
	 */
	int count(String dealerId) throws Exception;

	/**
	 * Method to update store information
	 * 
	 * @param dealerId
	 * @param storeId
	 * @param storeUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	Store updateStoreInformation(String dealerId, String storeId,
			String storename, String subCategory, String brands);

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
	StoreProductPricing updateStoreProductInformation(String dealerId,
			String productId, String storeName, String desc, String templateId,
			String sellingPrice, String listingStatus, String fulfilledBy,
			String nationalShippingCharge, String zonalShippingCharge,
			String localShippingCharge, String minNationalProcurementSLA,
			String maxNationalProcurementSLA, String minZonalProcurementSLA,
			String maxZonalProcurementSLA, String minLocalPocurementSLA,
			String maxLocalPocurementSLA, String minQuantityBuy,
			String stockCount, String tax, String mbgShare, String localRegion,
			String zonalRegion, String nationRegion, String discount,
			String willYouDeliver) throws Exception;

	/**
	 * Create new store
	 * 
	 * @param dealerId
	 * @param storeName
	 * @param desc
	 * @param templateId
	 * @param productIds
	 * @param mrp
	 * @param sellingPrice
	 * @param listingStatus
	 * @param fulfilledBy
	 * @param stockCount
	 * @param minQtyBuy
	 * @param tax
	 * @param mbgShare
	 * @param discount
	 * @param storeDelivery
	 * @param nationalShippingCharge
	 * @param nationalDelivery
	 * @param minNationalProcurementSLA
	 * @param maxNationalProcurementSLA
	 * @param zonalShippingCharge
	 * @param zonalDelivery
	 * @param minZonalProcurementSLA
	 * @param maxZonalProcurementSLA
	 * @param localShippingCharge
	 * @param localDelivery
	 * @param minLocalProcurementSLA
	 * @param maxLocalProcurementSLA
	 * @return
	 * @throws Exception
	 */
	Store createStore(String dealerId, String storeName, String desc,
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
			throws Exception;
}