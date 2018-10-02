package com.sarvah.mbg.catalog.service.impl;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.catalog.exception.ProductInputValidationException;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

@Service
public class ProductValidation {

	/**
	 * 
	 * @param product
	 * @return
	 * @throws ProductInputValidationException
	 */

	public boolean productValidation(Product product)
			throws ProductInputValidationException {
		boolean isValid = false;

		String skuid = product.getSkuId();
		String name = product.getName();
		String longName = product.getLongName();
		if (StringUtils.isNotBlank(skuid) && StringUtils.isNotBlank(name)
				&& StringUtils.isNotBlank(longName)) {

			isValid = true;

		} else {
			throw new ProductInputValidationException(
					"Product name or longname or Skuid or description is empty");
		}
		if (product.getDesc() != null) {
			isValid = true;
		} else {
			throw new ProductInputValidationException(
					"Product Description is empty");
		}
		if (product.getPrice() != null) {
			isValid = true;
		} else {
			throw new ProductInputValidationException("Product price is empty");
		}
		if (product.getSubcategories() != null) {
			Iterator iterator = product.getSubcategories().iterator();
			while (iterator.hasNext()) {
				SubCategory subCategory = (SubCategory) iterator.next();
				if (subCategory != null) {
					isValid = true;
					break;
				} else {
					throw new ProductInputValidationException(
							"Invalid Subcategories ID");
				}
			}

		} else {
			throw new ProductInputValidationException(
					"Product Subcategorues is empty");
		}
		if (product.getProductTypes() != null) {
			Iterator iterator = product.getProductTypes().iterator();
			while (iterator.hasNext()) {
				ProductType productType = (ProductType) iterator.next();
				if (productType != null) {
					isValid = true;
					break;
				} else {
					throw new ProductInputValidationException(
							"Invalid ProductType ID");
				}
			}
		} else {
			throw new ProductInputValidationException(
					"Product ProductTypes is empty");
		}
		if (product.getBrand() != null) {
			isValid = true;
		} else {
			throw new ProductInputValidationException(
					"Product Brand is empty Or Invalid");
		}
		if (product.getAttributes() != null) {
			Iterator iterator = product.getAttributes().iterator();
			while (iterator.hasNext()) {
				ProductAttribute productAttribute = (ProductAttribute) iterator
						.next();
				if (productAttribute != null) {
					isValid = true;
					break;
				} else {
					throw new ProductInputValidationException(
							"ProductAttribute is empty");
				}
			}
		}
		return isValid;
	}

	public boolean productAttributeValidation(ProductAttribute productAttribute)
			throws ProductInputValidationException {
		boolean isValid = false;
		if (StringUtils.isNotBlank(productAttribute.getKey())) {
			isValid = true;
		} else {
			throw new ProductInputValidationException(
					"ProductAttribute key is empty");
		}
		if (productAttribute.getValues() != null
				&& !productAttribute.getValues().isEmpty()) {
			isValid = true;
		} else {
			throw new ProductInputValidationException(
					"ProductAttribute value is empty");
		}
		return isValid;
	}

}
