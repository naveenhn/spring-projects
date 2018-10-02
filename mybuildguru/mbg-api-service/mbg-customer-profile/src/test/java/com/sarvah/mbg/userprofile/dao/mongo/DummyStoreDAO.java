/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;

/**
 * @author shivu s
 *
 */
public class DummyStoreDAO implements StoreDAO {

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends com.sarvah.mbg.domain.mongo.store.Store> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends com.sarvah.mbg.domain.mongo.store.Store> List<S> insert(
			Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends com.sarvah.mbg.domain.mongo.store.Store> List<S> save(
			Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<com.sarvah.mbg.domain.mongo.store.Store> findAll(
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends com.sarvah.mbg.domain.mongo.store.Store> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.sarvah.mbg.domain.mongo.store.Store findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<com.sarvah.mbg.domain.mongo.store.Store> findAll(
			Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(com.sarvah.mbg.domain.mongo.store.Store entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(
			Iterable<? extends com.sarvah.mbg.domain.mongo.store.Store> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public com.sarvah.mbg.domain.mongo.store.Store findByUserId(String dealerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<com.sarvah.mbg.domain.mongo.store.Store> findByUserId(
			String dealerId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByUserId(String dealerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<com.sarvah.mbg.domain.mongo.store.Store> findByProductsIn(
			Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByProducts(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findByProducts_IdAndUser_Addresses_LocationNear(
			String prodId, Point p, Distance d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByProducts(String uid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findByStoreProductPricings_ProductIdAndStoreProductPricings_ZonalDeliveryIsTrue(
			String id, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findByStoreProductPricings_ProductIdAndStoreProductPricings_NationalDeliveryIsTrue(
			String id, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.sarvah.mbg.domain.mongo.store.Store> findByStoreProductPricings_ProductIdAndLocalDeliveryIsTrueOrZonalDeliveryIsTrueOrNationalDeliveryIsTrue(
			String productId, boolean isLocalDelivery, boolean isZonalDelivery,
			boolean isNationalDelivery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.sarvah.mbg.domain.mongo.store.Store findByStorename(
			String storeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.sarvah.mbg.domain.mongo.store.Store findByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.sarvah.mbg.domain.mongo.store.Store findByIdAndUser(String sid,
			User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findBySubcategoriesInAndBrandsIn(
			Set<SubCategory> subCategoriesFinal, Set<ProductBrand> brands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findBySubcategoriesInAndProductsIn(
			Set<SubCategory> subCategoriesFinal, List<Product> products) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findBySubcategoriesIn(Set<SubCategory> subCategoriesFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findByBrandsIn(Set<ProductBrand> brands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findBySubcategoriesInAndBrandsInAndProductsIn(
			Set<SubCategory> subCategoriesFinal, Set<ProductBrand> brands,
			List<Product> products) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findByProductsIn(List<Product> products) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findByBrandsInAndProductsIn(Set<ProductBrand> brands,
			List<Product> products) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store findByUser_Id(String dealerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store findByUserAndProducts_Id(User user, String pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Store> findByBrandsIn(ProductBrand brand) {
		// TODO Auto-generated method stub
		return null;
	}
}
