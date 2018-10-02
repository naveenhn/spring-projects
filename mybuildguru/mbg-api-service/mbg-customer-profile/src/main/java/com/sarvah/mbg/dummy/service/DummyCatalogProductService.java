package com.sarvah.mbg.dummy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;

public class DummyCatalogProductService {
	public List<Product> getCatalogProducts()
	{
		List<Product> productSet=new ArrayList<>();
		Product product=new Product();
		product.setSkuId("1");
		product.setName("Jaquar Bathtub");
		Description desc=new Description("eng","Bathtub info");
		product.setDesc(desc);
		
		Set<SubCategory> subSet=new HashSet<>();
		SubCategory subCategory=new SubCategory();
		subCategory.setName("BathTub");
		subSet.add(subCategory);
		
		product.setSubcategories(subSet);
		
		Price price=new Price();
		price.setMrp(15000.00);
		product.setPrice(price);
		productSet.add(product);
		
		return productSet;
		
	}

}
