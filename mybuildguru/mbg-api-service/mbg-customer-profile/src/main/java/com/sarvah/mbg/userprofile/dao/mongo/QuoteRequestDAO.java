/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.QuoteStatus;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequest;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shivu
 *
 */
public interface QuoteRequestDAO extends MongoRepository<QuoteRequest, String> {

	Page<QuoteRequest> findByBuildOnnQuoteRequestIdLikeAllIgnoreCaseOrZipcodeOrFollowupDateLike(
			String quoteId, String zipcode, String followupDate,
			Pageable pageable);

	Page<QuoteRequest> findByCustomer(User customer, Pageable pageable);

	Page<QuoteRequest> findByCustomer_Id(String id, Pageable pageable);

	Page<QuoteRequest> findByCustomerAndZipcode(User customer,
			String searchValue, Pageable pageable);

	Page<QuoteRequest> findByCustomerAndBuildOnnQuoteRequestIdLike(
			User customer, String searchValue, Pageable pageable);

	QuoteRequest findByCustomerAndId(User customer, String quoteRequestId);

	Page<QuoteRequest> findByProductsInAndZipcode(
			Set<QuoteRequestProduct> quoteRequestProducts, String searchValue,
			Pageable pageable);

	Page<QuoteRequest> findByProductsInAndBuildOnnQuoteRequestIdLike(
			Set<QuoteRequestProduct> quoteRequestProducts, String searchValue,
			Pageable pageable);

	Page<QuoteRequest> findByProductsIn(
			Set<QuoteRequestProduct> quoteRequestProducts, Pageable pageable);

	QuoteRequest findByBuildOnnQuoteRequestId(String quoteRequestId);

	List<QuoteRequest> findByStatus(QuoteStatus status);

	int countByStatus(QuoteStatus status);

	int countByBuildOnnQuoteRequestId(String buildonnQuoteRequestId);

	int countByCustomerAndStatus(User customer, QuoteStatus status);

	Page<QuoteRequest> findByProductsInAndFollowupDateLike(
			Set<QuoteRequestProduct> quoteRequestProducts, String searchValue,
			Pageable pageable);

	Page<QuoteRequest> findByCustomerAndFollowupDateLike(User customer,
			String searchValue, Pageable pageable);

	Page<QuoteRequest> findByCustomer_IdAndStatus(String id,
			QuoteStatus valueOf, Pageable pageable);

	Page<QuoteRequest> findByStatus(QuoteStatus valueOf, Pageable pageable);

	Page<QuoteRequest> findByCustomerAndBuildOnnQuoteRequestIdLikeAllIgnoreCase(
			User customer, String searchValue, Pageable pageable);
}
