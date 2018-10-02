/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import com.sarvah.mbg.catalog.service.model.ProductCustomSearch;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;

/**
 * The Custom ProductDAOImpl.
 *
 * @author naveen
 */
public class ProductDAOImpl implements ProductDAOCustom {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductDAOImpl.class);

	/** The mongo operations. */
	private final MongoOperations mongoOperations;

	/**
	 * Instantiates a new product dao impl.
	 *
	 * @param mongoOperations
	 *            the mongo operations
	 */
	@Autowired
	public ProductDAOImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#searchProducts(com.
	 * sarvah.mbg.catalog.dao.mongo.ProductCustomSearch)
	 */
	@Override
	public List<Product> searchProducts(ProductCustomSearch productCustomSearch) {
		List<Product> products = null;
		Query query = getProductSearchQuery(productCustomSearch);
		if (productCustomSearch.getLimit() > 0) {
			products = mongoOperations.find(
					query.limit(productCustomSearch.getLimit()), Product.class);
		} else {
			products = mongoOperations.find(query, Product.class);
		}
		return products;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#searchProducts(com.
	 * sarvah.mbg.catalog.dao.mongo.ProductCustomSearch,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> searchProducts(
			ProductCustomSearch productCustomSearch, Pageable pageable) {
		Query query = getProductSearchQuery(productCustomSearch);

		Long count = count(productCustomSearch);
		List<Product> list = mongoOperations.find(query.with(pageable),
				Product.class);

		return new PageImpl<>(list, pageable, count);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#count(com.sarvah.mbg
	 * .catalog.dao.mongo.ProductCustomSearch)
	 */
	@Override
	public long count(ProductCustomSearch productCustomSearch) {
		Query query = getProductSearchQuery(productCustomSearch);
		return mongoOperations.count(query, Product.class);
	}

	@Override
	public List<Product> filterProducts(List<Product> products) {

		Product p0 = products.get(0);
		Collection<Product> ps = products
				.stream()
				.collect(
						Collectors.groupingBy(
								x -> {
									return new ArrayList<String>(Arrays
											.asList(getName(x.getName())));
								}, Collectors.reducing(p0, (x, y) -> y)))
				.values();
		ps.forEach(p -> p.setName(getName(p.getName())));
		return new ArrayList<Product>(ps);

	}

	private String getName(String name) {
		int index = name.lastIndexOf("-");
		if (index <= 0)
			return name;
		String ret = name.substring(0, index);
		return ret;
	}

	/**
	 * Add custom search logic.
	 *
	 * @param productCustomSearch
	 *            the product custom search
	 * @return the product search query
	 */
	private Query getProductSearchQuery(ProductCustomSearch productCustomSearch) {
		Assert.notNull(productCustomSearch,
				"Product Search object cannot be null");

		Query query = new Query();

		// if product ids are already given then why worry add that as first
		// search criteria
		query = checkAndAddCriteria(query,
				getProductIdsCriteria(productCustomSearch.getProductIds()));

		// catergory criteria
		query = checkAndAddCriteria(query,
				getCatergoriesCriteria(productCustomSearch.getCategories()));

		// Subcategory criteria
		query = checkAndAddCriteria(query,
				getSubCatergoriesCriteria(productCustomSearch
						.getSubCategoryNames()));

		// product type criteria
		query = checkAndAddCriteria(query,
				getProductTypesCriteria(productCustomSearch
						.getProductTypeNames()));

		// pricing criteria
		query = checkAndAddCriteria(
				query,
				getPricingCriteria(productCustomSearch.getMinPrice(),
						productCustomSearch.getMaxPrice()));

		// product brand criteria
		query = checkAndAddCriteria(query,
				getProductBrandCriteria(productCustomSearch
						.getProductBrandIds()));

		// product rating criteria
		query = checkAndAddCriteria(
				query,
				getProductRatingCriteria(productCustomSearch.getMinRating(),
						productCustomSearch.getMaxRating()));

		// product status criteria
		query = checkAndAddCriteria(query,
				getProductStatusCriteria(productCustomSearch
						.getProductStatuses()));

		// product text criteria
		query = checkAndAddCriteria(query,
				getTextSearchCriteria(productCustomSearch.getSearchText()));

		// attribute search criteria
		query = checkAndAddCriteria(query,
				getAttributeSearchCriteria(productCustomSearch
						.getAttributeSearchMap()));

		// restrict the fields you want to get from db
		query = addFields(query, productCustomSearch.getRequiredFields());

		return query;

	}

	/**
	 * Adds the fields.
	 *
	 * @param query
	 *            the query
	 * @param keys
	 *            the keys
	 * @return the query
	 */
	private Query addFields(Query query, Set<String> keys) {
		if (keys != null && !keys.isEmpty() && query != null) {
			for (String key : keys) {
				query.fields().include(key);
			}
		}

		return query;
	}

	/**
	 * Check and add criteria.
	 *
	 * @param query
	 *            the query
	 * @param attributSearchCriteria
	 *            the attribut search criteria
	 * @return the query
	 */
	private Query checkAndAddCriteria(Query query,
			Set<Criteria> attributSearchCriteria) {
		if (attributSearchCriteria != null && !attributSearchCriteria.isEmpty()) {
			Criteria[] criterasArray = attributSearchCriteria
					.toArray(new Criteria[attributSearchCriteria.size()]);
			query = checkAndAddCriteria(query, criterasArray);
		}

		return query;
	}

	/**
	 * Check and add criteria.
	 *
	 * @param query
	 *            the query
	 * @param criterias
	 *            the criterias
	 * @return the query
	 */
	private Query checkAndAddCriteria(Query query, Criteria... criterias) {
		Assert.notNull(query, "Query object cannot be null");
		if (criterias != null) {
			for (Criteria criteria : criterias) {
				if (criteria != null)
					query.addCriteria(criteria);
			}

		}

		return query;
	}

	/**
	 * Gets the sub catergories criteria.
	 *
	 * @param subcategoryNames
	 *            the subcategory names
	 * @return the sub catergories criteria
	 */
	private Criteria getSubCatergoriesCriteria(
			Collection<String> subcategoryNames) {
		if (subcategoryNames != null && !subcategoryNames.isEmpty()) {
			return where("subcategories.name").in(subcategoryNames);
		}
		return null;
	}

	/**
	 * Gets the catergories criteria.
	 *
	 * @param categories
	 *            the categories
	 * @return the catergories criteria
	 */
	private Criteria getCatergoriesCriteria(Collection<String> categories) {
		if (categories != null && !categories.isEmpty()) {
			return where("subcategories.category").in(categories);
		}
		return null;
	}

	/**
	 * Gets the product types criteria.
	 *
	 * @param productTypes
	 *            the product types
	 * @return the product types criteria
	 */
	private Criteria getProductTypesCriteria(Collection<String> productTypes) {
		if (productTypes != null && !productTypes.isEmpty()) {
			return where("productTypes.name").in(productTypes);
		}
		return null;
	}

	/**
	 * Gets the product brand criteria.
	 *
	 * @param productBrandIds
	 *            the product brand ids
	 * @return the product brand criteria
	 */
	private Criteria getProductBrandCriteria(Collection<String> productBrandIds) {
		if (productBrandIds != null && !productBrandIds.isEmpty()) {

			return where("brand.id").in(productBrandIds);
		}
		return null;
	}

	/**
	 * Gets the product status criteria.
	 *
	 * @param productStatuses
	 *            the product statuses
	 * @return the product status criteria
	 */
	private Criteria getProductStatusCriteria(
			Collection<ProductStatus> productStatuses) {
		if (productStatuses != null && !productStatuses.isEmpty()) {
			return where("status").in(productStatuses);
		}

		return null;
	}

	/**
	 * Gets the product rating criteria.
	 *
	 * @param minRating
	 *            the min rating
	 * @param maxRating
	 *            the max rating
	 * @return the product rating criteria
	 */
	private Criteria getProductRatingCriteria(Double minRating, Double maxRating) {
		if (minRating != null && maxRating != null) {
			return where("consolidatedRating.avgRating.ratingVal").gte(
					minRating).lte(maxRating);
		}
		return null;
	}

	/**
	 * Gets the pricing criteria.
	 *
	 * @param minPrice
	 *            the min price
	 * @param maxPrice
	 *            the max price
	 * @return the pricing criteria
	 */
	private Criteria getPricingCriteria(Double minPrice, Double maxPrice) {
		if (minPrice != null && maxPrice != null) {
			return where("price.mrp").gte(minPrice).lte(maxPrice);
		}
		return null;
	}

	/**
	 * Gets the pricing criteria.
	 *
	 * @param searchText
	 *            the search text
	 * @return the pricing criteria
	 */
	private Criteria getTextSearchCriteria(String searchText) {
		if (StringUtils.isNotBlank(searchText)) {
			Criteria cr = new Criteria();
			return cr.orOperator(where("name").regex(searchText, "i"),
					where("model").regex(searchText, "i"), where("longName")
							.regex(searchText, "i"),
					where("desc.val").regex(searchText, "i"),
					where("subcategories.name").regex(searchText, "i"));
		}
		return null;
	}

	/**
	 * Gets the product ids criteria.
	 *
	 * @param productIds
	 *            the product ids
	 * @return the product ids criteria
	 */
	private Criteria getProductIdsCriteria(Set<String> productIds) {
		if (productIds != null && !productIds.isEmpty()) {
			return where("id").in(productIds);
		}

		return null;
	}

	/**
	 * Gets the attribute search criteria.
	 *
	 * @param attributeKeyValueMap
	 *            the attribute key value map
	 * @return the attribute search criteria
	 */
	private Set<Criteria> getAttributeSearchCriteria(
			Map<String, List<String>> attributeKeyValueMap) {

		Set<Criteria> attributeCriterias = null;

		if (attributeKeyValueMap != null && !attributeKeyValueMap.isEmpty()) {
			attributeCriterias = new HashSet<>(attributeKeyValueMap.size());
			for (String attributeKey : attributeKeyValueMap.keySet()) {
				List<String> attributeValues = attributeKeyValueMap
						.get(attributeKey);
				if (attributeValues != null) {
					Criteria criteria = where("attributes.key")
							.in(attributeKey).andOperator(
									where("attributes.values").in(
											attributeValues));
					attributeCriterias.add(criteria);
				}

			}
		}

		return attributeCriterias;
	}

}
