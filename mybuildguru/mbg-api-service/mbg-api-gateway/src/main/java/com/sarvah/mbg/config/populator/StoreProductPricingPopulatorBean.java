/**
 * 
 */
package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;

/**
 * @author shivu
 *
 */
public class StoreProductPricingPopulatorBean {
	StoreProductPricingDAO storeProductPricingDAO;
	ProductDAO productDAO;
	StoreDAO productStoreDAO;

	StoreProductPricingPopulatorBean(
			StoreProductPricingDAO storeProductPricingDAO,
			ProductDAO productDAO, StoreDAO productStoreDAO) {
		this.storeProductPricingDAO = storeProductPricingDAO;
		this.productDAO = productDAO;
		this.productStoreDAO = productStoreDAO;
	}

	public void initStoreProductPricing() {
		storeProductPricingDAO.deleteAll();
		// // 1
		// Store store1 = productStoreDAO.findByStorename("Praveen Store");
		// List<Product> product1 = productDAO
		// .findByNameAllIgnoreCase("Jaguar - XYZ");
		// StoreProductPricing storeProductPricing1 = new StoreProductPricing(
		// store1.getId(), product1.get(0).getId(), product1.get(0)
		// .getSkuId());
		//
		// storeProductPricing1.setStoreProductId(storeProductPricing1
		// .getStoreProductId());
		//
		// storeProductPricing1.setProductId(product1.get(0).getId());
		//
		// storeProductPricing1.setSkuId(storeProductPricing1.getSkuId());
		//
		// storeProductPricing1.setStoreId(storeProductPricing1.getStoreId());
		//
		// storeProductPricing1.setMaxRetailPrice(product1.get(0).getPrice()
		// .getMrp());
		// System.out.println(product1.get(0).getPrice().getMrp());
		// System.out.println("29000.00");
		// storeProductPricing1.setSellingPrice(29000.00);
		//
		// storeProductPricing1.setFulfilledBy(store1.getUser().getFullName());
		//
		// storeProductPricing1.setStockCount(50);
		//
		// storeProductPricing1.setMinQuantityBuy(2);
		//
		// storeProductPricing1.setMbgShare(10.0);
		//
		// storeProductPricing1.setDiscount(10.0);
		//
		// Shipping Shipping1 = new Shipping();
		//
		// if (product1.get(0).getBrand() != null) {
		// Shipping1.setNationalShippingProvider(product1.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping1.setNationalShippingChrg(2500.00);
		// Shipping1.setNationalDelivery(true);
		//
		// if (product1.get(0).getBrand() != null) {
		// Shipping1.setZonalShippingProvider(product1.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping1.setZonalShippingChrg(700.00);
		// Shipping1.setZonalDelivery(true);
		//
		// if (product1.get(0).getBrand() != null) {
		// Shipping1.setLocalShippingProvider(product1.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping1.setLocalShippingChrg(500.00);
		// Shipping1.setLocalDelivery(true);
		//
		// Shipping1.setMinLocalProcurementSLA(2);
		// Shipping1.setMaxLocalProcurementSLA(4);
		//
		// Shipping1.setMinZonalProcurementSLA(4);
		// Shipping1.setMaxZonalProcurementSLA(6);
		//
		// Shipping1.setMinNationalProcurementSLA(6);
		// Shipping1.setMaxNationalProcurementSLA(8);
		//
		// storeProductPricing1.setShipping(Shipping1);
		// storeProductPricingDAO.insert(storeProductPricing1);
		//
		// // 2
		// List<Product> product11 = productDAO
		// .findByNameAllIgnoreCase("Cera - XYZ");
		//
		// StoreProductPricing storeProductPricing11 = new StoreProductPricing(
		// store1.getId(), product11.get(0).getId(), product11.get(0)
		// .getSkuId());
		//
		// storeProductPricing11.setStoreProductId(storeProductPricing11
		// .getStoreProductId());
		//
		// storeProductPricing11.setProductId(product11.get(0).getId());
		//
		// storeProductPricing11.setSkuId(storeProductPricing11.getSkuId());
		//
		// storeProductPricing11.setStoreId(storeProductPricing11.getStoreId());
		//
		// storeProductPricing11.setMaxRetailPrice(product11.get(0).getPrice()
		// .getMrp());
		// System.out.println(product11.get(0).getPrice().getMrp());
		// System.out.println("88000.00");
		// storeProductPricing11.setSellingPrice(88000.00);
		//
		// storeProductPricing11.setFulfilledBy(store1.getUser().getFullName());
		//
		// storeProductPricing11.setStockCount(50);
		//
		// storeProductPricing11.setMinQuantityBuy(2);
		//
		// storeProductPricing11.setMbgShare(10.0);
		//
		// storeProductPricing11.setDiscount(10.0);
		//
		// Shipping Shipping11 = new Shipping();
		//
		// if (product11.get(0).getBrand() != null) {
		// Shipping11.setNationalShippingProvider(product11.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping11.setNationalShippingChrg(2200.00);
		// Shipping11.setNationalDelivery(true);
		//
		// if (product11.get(0).getBrand() != null) {
		// Shipping11.setZonalShippingProvider(product11.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping11.setZonalShippingChrg(1700.00);
		// Shipping11.setZonalDelivery(true);
		//
		// if (product11.get(0).getBrand() != null) {
		// Shipping11.setLocalShippingProvider(product11.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping11.setLocalShippingChrg(1500.00);
		// Shipping11.setLocalDelivery(true);
		//
		// Shipping11.setMinLocalProcurementSLA(2);
		// Shipping11.setMaxLocalProcurementSLA(4);
		//
		// Shipping11.setMinZonalProcurementSLA(3);
		// Shipping11.setMaxZonalProcurementSLA(5);
		//
		// Shipping11.setMinNationalProcurementSLA(4);
		// Shipping11.setMaxNationalProcurementSLA(8);
		//
		// storeProductPricing11.setShipping(Shipping11);
		// storeProductPricingDAO.insert(storeProductPricing11);
		//
		// // 3
		// List<Product> product111 = productDAO
		// .findByNameAllIgnoreCase("Toto - MNO");
		//
		// StoreProductPricing storeProductPricing111 = new StoreProductPricing(
		// store1.getId(), product111.get(0).getId(), product111.get(0)
		// .getSkuId());
		//
		// storeProductPricing111.setStoreProductId(storeProductPricing111
		// .getStoreProductId());
		//
		// storeProductPricing111.setProductId(product111.get(0).getId());
		//
		// storeProductPricing111.setSkuId(storeProductPricing111.getSkuId());
		//
		// storeProductPricing111.setStoreId(storeProductPricing111.getStoreId());
		//
		// storeProductPricing111.setMaxRetailPrice(product111.get(0).getPrice()
		// .getMrp());
		// System.out.println(product111.get(0).getPrice().getMrp());
		// System.out.println("63000.00");
		// storeProductPricing111.setSellingPrice(63000.00);
		//
		// storeProductPricing111.setFulfilledBy(store1.getUser().getFullName());
		//
		// storeProductPricing111.setStockCount(50);
		//
		// storeProductPricing111.setMinQuantityBuy(2);
		//
		// storeProductPricing111.setMbgShare(10.0);
		//
		// storeProductPricing111.setDiscount(10.0);
		//
		// Shipping Shipping111 = new Shipping();
		//
		// if (product111.get(0).getBrand() != null) {
		// Shipping111.setNationalShippingProvider(product111.get(0)
		// .getBrand().getProvider().getFullName());
		// }
		// Shipping111.setNationalShippingChrg(1500.00);
		// Shipping111.setNationalDelivery(true);
		//
		// if (product111.get(0).getBrand() != null) {
		// Shipping111.setZonalShippingProvider(product111.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping111.setZonalShippingChrg(1000.00);
		// Shipping111.setZonalDelivery(true);
		//
		// if (product111.get(0).getBrand() != null) {
		// Shipping111.setLocalShippingProvider(product111.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping111.setLocalShippingChrg(700.00);
		// Shipping111.setLocalDelivery(true);
		//
		// Shipping111.setMinLocalProcurementSLA(2);
		// Shipping111.setMaxLocalProcurementSLA(4);
		//
		// Shipping111.setMinZonalProcurementSLA(5);
		// Shipping111.setMaxZonalProcurementSLA(6);
		//
		// Shipping111.setMinNationalProcurementSLA(4);
		// Shipping111.setMaxNationalProcurementSLA(7);
		//
		// storeProductPricing111.setShipping(Shipping111);
		// storeProductPricingDAO.insert(storeProductPricing111);
		//
		// // 4
		// Store store2 = productStoreDAO.findByStorename("Kiran Store");
		//
		// List<Product> product2 = productDAO
		// .findByNameAllIgnoreCase("Cera - ABC");
		//
		// StoreProductPricing storeProductPricing2 = new StoreProductPricing(
		// store2.getId(), product2.get(0).getId(), product2.get(0)
		// .getSkuId());
		//
		// storeProductPricing2.setStoreProductId(storeProductPricing2
		// .getStoreProductId());
		//
		// storeProductPricing2.setProductId(product2.get(0).getId());
		//
		// storeProductPricing2.setSkuId(storeProductPricing2.getSkuId());
		//
		// storeProductPricing2.setStoreId(storeProductPricing2.getStoreId());
		//
		// storeProductPricing2.setMaxRetailPrice(product2.get(0).getPrice()
		// .getMrp());
		// System.out.println(product2.get(0).getPrice().getMrp());
		// System.out.println("49000.00");
		// storeProductPricing2.setSellingPrice(49000.00);
		//
		// storeProductPricing2.setFulfilledBy(store2.getUser().getFullName());
		//
		// storeProductPricing2.setStockCount(50);
		//
		// storeProductPricing2.setMinQuantityBuy(2);
		//
		// storeProductPricing2.setMbgShare(10.0);
		//
		// storeProductPricing2.setDiscount(10.0);
		//
		// Shipping Shipping2 = new Shipping();
		//
		// Shipping2.setNationalShippingProvider(null);
		// Shipping2.setNationalShippingChrg(null);
		// Shipping2.setNationalDelivery(false);
		//
		// if (product2.get(0).getBrand() != null) {
		// Shipping2.setZonalShippingProvider(product2.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping2.setZonalShippingChrg(1000.00);
		// Shipping2.setZonalDelivery(true);
		//
		// if (product2.get(0).getBrand() != null) {
		// Shipping2.setLocalShippingProvider(product2.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping2.setLocalShippingChrg(800.00);
		// Shipping2.setLocalDelivery(true);
		//
		// Shipping2.setMinLocalProcurementSLA(4);
		// Shipping2.setMaxLocalProcurementSLA(5);
		//
		// Shipping2.setMinZonalProcurementSLA(5);
		// Shipping2.setMaxZonalProcurementSLA(7);
		//
		// Shipping2.setMinNationalProcurementSLA(5);
		// Shipping2.setMaxNationalProcurementSLA(9);
		//
		// storeProductPricing2.setShipping(Shipping2);
		// storeProductPricingDAO.insert(storeProductPricing2);
		//
		// // 5
		// List<Product> product22 = productDAO
		// .findByNameAllIgnoreCase("Hindware - XYZ");
		//
		// StoreProductPricing storeProductPricing22 = new StoreProductPricing(
		// store2.getId(), product22.get(0).getId(), product22.get(0)
		// .getSkuId());
		//
		// storeProductPricing22.setStoreProductId(storeProductPricing22
		// .getStoreProductId());
		//
		// storeProductPricing22.setProductId(product22.get(0).getId());
		//
		// storeProductPricing22.setSkuId(storeProductPricing22.getSkuId());
		//
		// storeProductPricing22.setStoreId(storeProductPricing22.getStoreId());
		//
		// storeProductPricing22.setMaxRetailPrice(product22.get(0).getPrice()
		// .getMrp());
		// System.out.println(product22.get(0).getPrice().getMrp());
		// System.out.println("23000.00");
		// storeProductPricing22.setSellingPrice(23000.00);
		//
		// storeProductPricing22.setFulfilledBy(store2.getUser().getFullName());
		//
		// storeProductPricing22.setStockCount(50);
		//
		// storeProductPricing22.setMinQuantityBuy(2);
		//
		// storeProductPricing22.setMbgShare(10.0);
		//
		// storeProductPricing22.setDiscount(10.0);
		//
		// Shipping Shipping22 = new Shipping();
		//
		// Shipping22.setNationalShippingProvider(null);
		// Shipping22.setNationalShippingChrg(null);
		// Shipping22.setNationalDelivery(false);
		//
		// if (product22.get(0).getBrand() != null) {
		// Shipping22.setZonalShippingProvider(product22.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping22.setZonalShippingChrg(300.00);
		// Shipping22.setZonalDelivery(true);
		//
		// if (product22.get(0).getBrand() != null) {
		// Shipping22.setLocalShippingProvider(product22.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping22.setLocalShippingChrg(100.00);
		// Shipping22.setLocalDelivery(true);
		//
		// Shipping22.setMinLocalProcurementSLA(3);
		// Shipping22.setMaxLocalProcurementSLA(5);
		//
		// Shipping22.setMinZonalProcurementSLA(5);
		// Shipping22.setMaxZonalProcurementSLA(7);
		//
		// Shipping22.setMinNationalProcurementSLA(6);
		// Shipping22.setMaxNationalProcurementSLA(9);
		//
		// storeProductPricing22.setShipping(Shipping22);
		// storeProductPricingDAO.insert(storeProductPricing22);
		//
		// // 6
		// List<Product> product222 = productDAO
		// .findByNameAllIgnoreCase("Hindware - PQR");
		//
		// StoreProductPricing storeProductPricing222 = new StoreProductPricing(
		// store2.getId(), product222.get(0).getId(), product222.get(0)
		// .getSkuId());
		//
		// storeProductPricing222.setStoreProductId(storeProductPricing222
		// .getStoreProductId());
		//
		// storeProductPricing222.setProductId(product222.get(0).getId());
		//
		// storeProductPricing222.setSkuId(storeProductPricing222.getSkuId());
		//
		// storeProductPricing222.setStoreId(storeProductPricing222.getStoreId());
		//
		// storeProductPricing222.setMaxRetailPrice(product222.get(0).getPrice()
		// .getMrp());
		// System.out.println(product222.get(0).getPrice().getMrp());
		// System.out.println("45000.00");
		// storeProductPricing222.setSellingPrice(45000.00);
		//
		// storeProductPricing222.setFulfilledBy(store2.getUser().getFullName());
		//
		// storeProductPricing222.setStockCount(50);
		//
		// storeProductPricing222.setMinQuantityBuy(2);
		//
		// storeProductPricing222.setMbgShare(10.0);
		//
		// storeProductPricing222.setDiscount(10.0);
		//
		// Shipping Shipping222 = new Shipping();
		//
		// Shipping222.setNationalShippingProvider(null);
		// Shipping222.setNationalShippingChrg(null);
		// Shipping222.setNationalDelivery(false);
		//
		// if (product222.get(0).getBrand() != null) {
		// Shipping222.setZonalShippingProvider(product222.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping222.setZonalShippingChrg(1200.00);
		// Shipping222.setZonalDelivery(true);
		//
		// if (product222.get(0).getBrand() != null) {
		// Shipping222.setLocalShippingProvider(product222.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping222.setLocalShippingChrg(1000.00);
		// Shipping222.setLocalDelivery(true);
		//
		// Shipping222.setMinLocalProcurementSLA(1);
		// Shipping222.setMaxLocalProcurementSLA(3);
		//
		// Shipping222.setMinZonalProcurementSLA(4);
		// Shipping222.setMaxZonalProcurementSLA(6);
		//
		// Shipping222.setMinNationalProcurementSLA(5);
		// Shipping222.setMaxNationalProcurementSLA(8);
		//
		// storeProductPricing222.setShipping(Shipping222);
		// storeProductPricingDAO.insert(storeProductPricing222);
		//
		// // 7
		// Store store3 = productStoreDAO.findByStorename("Naveen Store");
		//
		// List<Product> product3 = productDAO
		// .findByNameAllIgnoreCase("Jaguar - XYZ");
		//
		// StoreProductPricing storeProductPricing3 = new StoreProductPricing(
		// store3.getId(), product3.get(0).getId(), product3.get(0)
		// .getSkuId());
		//
		// storeProductPricing3.setStoreProductId(storeProductPricing3
		// .getStoreProductId());
		//
		// storeProductPricing3.setProductId(product3.get(0).getId());
		//
		// storeProductPricing3.setSkuId(storeProductPricing3.getSkuId());
		//
		// storeProductPricing3.setStoreId(storeProductPricing3.getStoreId());
		//
		// storeProductPricing3.setMaxRetailPrice(product3.get(0).getPrice()
		// .getMrp());
		// System.out.println(product3.get(0).getPrice().getMrp());
		// System.out.println("28000.00");
		// storeProductPricing3.setSellingPrice(28000.00);
		//
		// storeProductPricing3.setFulfilledBy(store3.getUser().getFullName());
		//
		// storeProductPricing3.setStockCount(50);
		//
		// storeProductPricing3.setMinQuantityBuy(2);
		//
		// storeProductPricing3.setMbgShare(10.0);
		//
		// storeProductPricing3.setDiscount(10.0);
		//
		// Shipping Shipping3 = new Shipping();
		//
		// if (product3.get(0).getBrand() != null) {
		// Shipping3.setNationalShippingProvider(product3.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping3.setNationalShippingChrg(1250.00);
		// Shipping3.setNationalDelivery(true);
		//
		// if (product3.get(0).getBrand() != null) {
		// Shipping3.setZonalShippingProvider(product3.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping3.setZonalShippingChrg(700.00);
		// Shipping3.setZonalDelivery(true);
		//
		// if (product3.get(0).getBrand() != null) {
		// Shipping3.setLocalShippingProvider(product3.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping3.setLocalShippingChrg(450.00);
		// Shipping3.setLocalDelivery(true);
		//
		// Shipping3.setMinLocalProcurementSLA(1);
		// Shipping3.setMaxLocalProcurementSLA(3);
		//
		// Shipping3.setMinZonalProcurementSLA(2);
		// Shipping3.setMaxZonalProcurementSLA(5);
		//
		// Shipping3.setMinNationalProcurementSLA(4);
		// Shipping3.setMaxNationalProcurementSLA(6);
		//
		// storeProductPricing3.setShipping(Shipping3);
		// storeProductPricingDAO.insert(storeProductPricing3);
		//
		// // 8
		// List<Product> product33 = productDAO
		// .findByNameAllIgnoreCase("Hindware - XYZ");
		//
		// StoreProductPricing storeProductPricing33 = new StoreProductPricing(
		// store3.getId(), product33.get(0).getId(), product33.get(0)
		// .getSkuId());
		//
		// storeProductPricing33.setStoreProductId(storeProductPricing33
		// .getStoreProductId());
		//
		// storeProductPricing33.setProductId(product33.get(0).getId());
		//
		// storeProductPricing33.setSkuId(storeProductPricing33.getSkuId());
		//
		// storeProductPricing33.setStoreId(storeProductPricing33.getStoreId());
		//
		// storeProductPricing33.setMaxRetailPrice(product33.get(0).getPrice()
		// .getMrp());
		// System.out.println(product33.get(0).getPrice().getMrp());
		// System.out.println("23000.00");
		// storeProductPricing33.setSellingPrice(23000.00);
		//
		// storeProductPricing33.setFulfilledBy(store3.getUser().getFullName());
		//
		// storeProductPricing33.setStockCount(50);
		//
		// storeProductPricing33.setMinQuantityBuy(2);
		//
		// storeProductPricing33.setMbgShare(10.0);
		//
		// storeProductPricing33.setDiscount(10.0);
		//
		// Shipping Shipping33 = new Shipping();
		//
		// if (product33.get(0).getBrand() != null) {
		// Shipping33.setNationalShippingProvider(product33.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping33.setNationalShippingChrg(1250.00);
		// Shipping33.setNationalDelivery(true);
		//
		// if (product33.get(0).getBrand() != null) {
		// Shipping33.setZonalShippingProvider(product33.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping33.setZonalShippingChrg(700.00);
		// Shipping33.setZonalDelivery(true);
		//
		// if (product33.get(0).getBrand() != null) {
		// Shipping33.setLocalShippingProvider(product33.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping33.setLocalShippingChrg(450.00);
		// Shipping33.setLocalDelivery(true);
		//
		// Shipping33.setMinLocalProcurementSLA(2);
		// Shipping33.setMaxLocalProcurementSLA(5);
		//
		// Shipping33.setMinZonalProcurementSLA(3);
		// Shipping33.setMaxZonalProcurementSLA(7);
		//
		// Shipping33.setMinNationalProcurementSLA(5);
		// Shipping33.setMaxNationalProcurementSLA(9);
		//
		// storeProductPricing33.setShipping(Shipping33);
		// storeProductPricingDAO.insert(storeProductPricing33);
		//
		// // 9
		// List<Product> product333 = productDAO
		// .findByNameAllIgnoreCase("Toto - MNO");
		//
		// StoreProductPricing storeProductPricing333 = new StoreProductPricing(
		// store3.getId(), product333.get(0).getId(), product333.get(0)
		// .getSkuId());
		//
		// storeProductPricing333.setStoreProductId(storeProductPricing333
		// .getStoreProductId());
		//
		// storeProductPricing333.setProductId(product333.get(0).getId());
		//
		// storeProductPricing333.setSkuId(storeProductPricing333.getSkuId());
		//
		// storeProductPricing333.setStoreId(storeProductPricing333.getStoreId());
		//
		// storeProductPricing333.setMaxRetailPrice(product333.get(0).getPrice()
		// .getMrp());
		// System.out.println(product333.get(0).getPrice().getMrp());
		// System.out.println("63000.00");
		// storeProductPricing333.setSellingPrice(63000.00);
		//
		// storeProductPricing333.setFulfilledBy(store3.getUser().getFullName());
		//
		// storeProductPricing333.setStockCount(50);
		//
		// storeProductPricing333.setMinQuantityBuy(2);
		//
		// storeProductPricing333.setMbgShare(10.0);
		//
		// storeProductPricing333.setDiscount(10.0);
		//
		// Shipping Shipping333 = new Shipping();
		//
		// if (product333.get(0).getBrand() != null) {
		// Shipping333.setNationalShippingProvider(product333.get(0)
		// .getBrand().getProvider().getFullName());
		// }
		// Shipping333.setNationalShippingChrg(1250.00);
		// Shipping333.setNationalDelivery(true);
		//
		// if (product333.get(0).getBrand() != null) {
		// Shipping333.setZonalShippingProvider(product333.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping333.setZonalShippingChrg(700.00);
		// Shipping333.setZonalDelivery(true);
		//
		// if (product333.get(0).getBrand() != null) {
		// Shipping333.setLocalShippingProvider(product333.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping333.setLocalShippingChrg(450.00);
		// Shipping333.setLocalDelivery(true);
		//
		// Shipping333.setMinLocalProcurementSLA(1);
		// Shipping333.setMaxLocalProcurementSLA(3);
		//
		// Shipping333.setMinZonalProcurementSLA(5);
		// Shipping333.setMaxZonalProcurementSLA(7);
		//
		// Shipping333.setMinNationalProcurementSLA(7);
		// Shipping333.setMaxNationalProcurementSLA(10);
		//
		// storeProductPricing333.setShipping(Shipping333);
		// storeProductPricingDAO.insert(storeProductPricing333);
		//
		// // 10
		// Store store4 = productStoreDAO.findByStorename("Sangeetha Store");
		// List<Product> product4 = productDAO
		// .findByNameAllIgnoreCase("Cera - ABC");
		//
		// StoreProductPricing storeProductPricing4 = new StoreProductPricing(
		// store4.getId(), product4.get(0).getId(), product4.get(0)
		// .getSkuId());
		//
		// storeProductPricing4.setStoreProductId(storeProductPricing4
		// .getStoreProductId());
		//
		// storeProductPricing4.setProductId(product4.get(0).getId());
		//
		// storeProductPricing4.setSkuId(storeProductPricing4.getSkuId());
		//
		// storeProductPricing4.setStoreId(storeProductPricing4.getStoreId());
		//
		// storeProductPricing4.setMaxRetailPrice(product4.get(0).getPrice()
		// .getMrp());
		// System.out.println(product4.get(0).getPrice().getMrp());
		// System.out.println("49000.00");
		// storeProductPricing4.setSellingPrice(49000.00);
		//
		// storeProductPricing4.setFulfilledBy(store4.getUser().getFullName());
		//
		// storeProductPricing4.setStockCount(50);
		//
		// storeProductPricing4.setMinQuantityBuy(2);
		//
		// storeProductPricing4.setMbgShare(10.0);
		//
		// storeProductPricing4.setDiscount(10.0);
		//
		// Shipping Shipping4 = new Shipping();
		//
		// if (product4.get(0).getBrand() != null) {
		// Shipping4.setNationalShippingProvider(product4.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping4.setNationalShippingChrg(1250.00);
		// Shipping4.setNationalDelivery(true);
		//
		// if (product4.get(0).getBrand() != null) {
		// Shipping4.setZonalShippingProvider(product4.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping4.setZonalShippingChrg(700.00);
		// Shipping4.setZonalDelivery(true);
		//
		// if (product4.get(0).getBrand() != null) {
		// Shipping4.setLocalShippingProvider(product4.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping4.setLocalShippingChrg(450.00);
		// Shipping4.setLocalDelivery(true);
		//
		// Shipping4.setMinLocalProcurementSLA(1);
		// Shipping4.setMaxLocalProcurementSLA(3);
		//
		// Shipping4.setMinZonalProcurementSLA(5);
		// Shipping4.setMaxZonalProcurementSLA(7);
		//
		// Shipping4.setMinNationalProcurementSLA(7);
		// Shipping4.setMaxNationalProcurementSLA(10);
		//
		// storeProductPricing4.setShipping(Shipping4);
		// storeProductPricingDAO.insert(storeProductPricing4);
		//
		// // 11
		// List<Product> product44 = productDAO
		// .findByNameAllIgnoreCase("Cera - XYZ");
		//
		// StoreProductPricing storeProductPricing44 = new StoreProductPricing(
		// store4.getId(), product44.get(0).getId(), product44.get(0)
		// .getSkuId());
		//
		// storeProductPricing44.setStoreProductId(storeProductPricing44
		// .getStoreProductId());
		//
		// storeProductPricing44.setProductId(product44.get(0).getId());
		//
		// storeProductPricing44.setSkuId(storeProductPricing44.getSkuId());
		//
		// storeProductPricing44.setStoreId(storeProductPricing44.getStoreId());
		//
		// storeProductPricing44.setMaxRetailPrice(product44.get(0).getPrice()
		// .getMrp());
		// System.out.println(product44.get(0).getPrice().getMrp());
		// System.out.println("88000.00");
		// storeProductPricing44.setSellingPrice(88000.00);
		//
		// storeProductPricing44.setFulfilledBy(store4.getUser().getFullName());
		//
		// storeProductPricing44.setStockCount(50);
		//
		// storeProductPricing44.setMinQuantityBuy(2);
		//
		// storeProductPricing44.setMbgShare(10.0);
		//
		// storeProductPricing44.setDiscount(10.0);
		//
		// Shipping Shipping44 = new Shipping();
		//
		// if (product44.get(0).getBrand() != null) {
		// Shipping44.setNationalShippingProvider(product44.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping44.setNationalShippingChrg(1000.00);
		// Shipping44.setNationalDelivery(true);
		//
		// if (product44.get(0).getBrand() != null) {
		// Shipping44.setZonalShippingProvider(product44.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping44.setZonalShippingChrg(550.00);
		// Shipping44.setZonalDelivery(true);
		//
		// if (product44.get(0).getBrand() != null) {
		// Shipping44.setLocalShippingProvider(product44.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping44.setLocalShippingChrg(450.00);
		// Shipping44.setLocalDelivery(true);
		//
		// Shipping44.setMinLocalProcurementSLA(1);
		// Shipping44.setMaxLocalProcurementSLA(3);
		//
		// Shipping44.setMinZonalProcurementSLA(4);
		// Shipping44.setMaxZonalProcurementSLA(7);
		//
		// Shipping44.setMinNationalProcurementSLA(7);
		// Shipping44.setMaxNationalProcurementSLA(11);
		//
		// storeProductPricing44.setShipping(Shipping44);
		// storeProductPricingDAO.insert(storeProductPricing44);
		//
		// // 12
		// List<Product> product444 = productDAO
		// .findByNameAllIgnoreCase("Hindware - PQR");
		//
		// StoreProductPricing storeProductPricing444 = new StoreProductPricing(
		// store4.getId(), product444.get(0).getId(), product444.get(0)
		// .getSkuId());
		//
		// storeProductPricing444.setStoreProductId(storeProductPricing444
		// .getStoreProductId());
		//
		// storeProductPricing444.setProductId(product444.get(0).getId());
		//
		// storeProductPricing444.setSkuId(storeProductPricing444.getSkuId());
		//
		// storeProductPricing444.setStoreId(storeProductPricing444.getStoreId());
		//
		// storeProductPricing444.setMaxRetailPrice(product444.get(0).getPrice()
		// .getMrp());
		// System.out.println(product444.get(0).getPrice().getMrp());
		// System.out.println("45000.00");
		// storeProductPricing444.setSellingPrice(45000.00);
		//
		// storeProductPricing444.setFulfilledBy(store4.getUser().getFullName());
		//
		// storeProductPricing444.setStockCount(50);
		//
		// storeProductPricing444.setMinQuantityBuy(2);
		//
		// storeProductPricing444.setMbgShare(10.0);
		//
		// storeProductPricing444.setDiscount(10.0);
		//
		// Shipping Shipping444 = new Shipping();
		//
		// if (product444.get(0).getBrand() != null) {
		// Shipping444.setNationalShippingProvider(product444.get(0)
		// .getBrand().getProvider().getFullName());
		// }
		// Shipping444.setNationalShippingChrg(1000.00);
		// Shipping444.setNationalDelivery(true);
		//
		// if (product444.get(0).getBrand() != null) {
		// Shipping444.setZonalShippingProvider(product444.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping444.setZonalShippingChrg(550.00);
		// Shipping444.setZonalDelivery(true);
		//
		// if (product444.get(0).getBrand() != null) {
		// Shipping444.setLocalShippingProvider(product444.get(0).getBrand()
		// .getProvider().getFullName());
		// }
		// Shipping444.setLocalShippingChrg(450.00);
		// Shipping444.setLocalDelivery(true);
		//
		// Shipping444.setMinLocalProcurementSLA(1);
		// Shipping444.setMaxLocalProcurementSLA(2);
		//
		// Shipping444.setMinZonalProcurementSLA(3);
		// Shipping444.setMaxZonalProcurementSLA(6);
		//
		// Shipping444.setMinNationalProcurementSLA(7);
		// Shipping444.setMaxNationalProcurementSLA(12);
		//
		// storeProductPricing444.setShipping(Shipping444);
		// storeProductPricingDAO.insert(storeProductPricing444);
	}
}
