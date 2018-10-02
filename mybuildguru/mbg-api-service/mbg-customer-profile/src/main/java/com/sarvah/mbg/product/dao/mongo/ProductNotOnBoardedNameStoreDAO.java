/**
 * 
 */
package com.sarvah.mbg.product.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;

/**
 * @author Shivu
 *
 */
public interface ProductNotOnBoardedNameStoreDAO extends
		MongoRepository<ProductNotOnBoardedNameStore, String> {

	List<ProductNotOnBoardedNameStore> findByNameAllIgnoreCase(String itemName);

}
