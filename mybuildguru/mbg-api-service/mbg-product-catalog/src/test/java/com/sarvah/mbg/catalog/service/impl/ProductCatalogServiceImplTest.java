///**
// * 
// */
//package com.sarvah.mbg.catalog.service.impl;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sarvah.mbg.catalog.MbgProductCatalogTestConfiguration;
//import com.sarvah.mbg.catalog.service.ProductCatalogService;
//import com.sarvah.mbg.catalog.service.ProductCategoryService;
//import com.sarvah.mbg.domain.mongo.catalog.Category;
//import com.sarvah.mbg.domain.mongo.catalog.Product;
//import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
//import com.sarvah.mbg.domain.mongo.marketing.Promotion;
//import com.sarvah.mbg.promotion.service.PromotionService;
//
///**
// * @author shivu
// *
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MbgProductCatalogTestConfiguration.class)
//public class ProductCatalogServiceImplTest {
//
//	@Autowired
//	private ProductCatalogService productCatalogService;
//
//	@Autowired
//	private PromotionService promotionService;
//
//	@Autowired
//	private ProductCategoryService productCategoryService;
//
//	@Test
//	public void test() {
//		assertEquals(1, 1);
//	}
//
//	@Test
//	public void testGetProductsForUser() throws Exception {
//		Page<Product> product;
//		try {
//			product = productCatalogService.getProductsForUser("123");
//			assertTrue(product.getSize() > 1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testGetProductsCountForUser() throws Exception {
//		long count = 0;
//		try {
//			count = productCatalogService.getProductsCountForUser("123");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		assertEquals(20l, count);
//	}
//
//	@Test
//	public void testGetPromotionForProduct() throws Exception {
//		List<Promotion> promotion = promotionService
//				.getPromotionForProduct("123", null, null, null, null, null);
//
//		assertTrue(promotion.size() == 1);
//
//		List<Promotion> promotion1 = promotionService
//				.getPromotionForProduct("123", "Sunday_Promotion", null, null,
//						null, null);
//
//		assertTrue(promotion1.size() == 1);
//
//	}
//
//	@Test
//	public void testGetPromotionCount() throws Exception {
//
//		long count = promotionService.getPromotionCount("11",
//				"Sunday_Promotion", null, null, null, null);
//		assertEquals(10, count);
//	}
//
//	@Test
//	public void testCreatePromotionForProduct() throws Exception {
//		Promotion promotion = promotionService.createPromotionForProduct(
//				"123", "Sunday_Promotion", "Sunday_Promotion info", "DISCOUNT",
//				null, "35", "04/09/2015", "10/09/2015");
//		assertEquals("Sunday_Promotion", promotion.getName());
//	}
//
//	@Test
//	public void testUpdatePromotion() throws Exception {
//
//		Promotion promotion = promotionService.updatePromotion("11", "22",
//				"New year offer sale", null, null, null, null, null, null);
//		assertEquals("New year offer sale", promotion.getName());
//	}
//
//	@Test
//	public void testDeletePromotionForProduct() throws Exception {
//		String promId = promotionService.deletePromotionForProduct("123",
//				"1");
//		assertEquals("1", promId);
//	}
//
//	// product
//
//	@Test
//	public void testsearchCategories() {
//
//		List<Category> categories = productCategoryService
//				.searchCategories("Painting");
//		assertEquals("abc", categories.get(0).getName());
//		assertTrue(categories.size() == 1);
//	}
//
//	@Test
//	public void testGetCategoryCount() {
//
//		long count1 = productCategoryService.getCategoryCount();
//		assertEquals(20l, count1);
//
//		long count2 = productCategoryService.getCategoryCount("Plumbing");
//		assertEquals(20l, count2);
//	}
//
//	@Test
//	public void testCreateCategory() throws Exception {
//		Category category = productCategoryService.createCategory("Bath tub123",
//				"Bath tub123 info");
//		assertEquals("Bath tub123", category.getName());
//		assertEquals("Bath tub123 info", category.getDesc().getVal());
//	}
//
//	@Test
//	public void testsearchCategoriesById() {
//		Category category = productCategoryService.searchCategoriesById("123");
//		assertEquals("123", category.getId());
//	}
//
//	@Test
//	public void testUpdateCategory() throws Exception {
//		Category category = productCategoryService.updateCategory("123",
//				"Bath tub", "Bath tub info");
//		assertEquals("Bath tub", category.getName());
//		assertEquals("Bath tub info", category.getDesc().getVal());
//	}
//
//	@Test
//	public void testDeleteCategory() throws Exception {
//		String catid = productCategoryService.deleteCategory("111");
//		assertEquals("111", catid);
//	}
//
//	@Test
//	public void testSearchSubCategories() {
//		List<SubCategory> subCategories = productCategoryService
//				.searchSubCategories("123");
//		assertEquals("tap", subCategories.get(0).getName());
//	}
//
//	@Test
//	public void testSearchSubCategorieById() {
//		SubCategory subCategories = productCategoryService
//				.searchSubCategorieById("123", "124");
//		assertEquals("124", subCategories.getId());
//	}
//
//	@Test
//	public void testCreateSubCategory() throws Exception {
//
//		SubCategory subCategory = productCategoryService.createSubCategory("11",
//				"LED Bulbs info", "LED Bulbs");
//		assertEquals("LED Bulbs", subCategory.getName());
//
//	}
//
//	//
//	// @Test
//	// public void testUpdateSubCategory() throws Exception {
//	// SubCategory subCategory = productCatalogService.updateSubCategory("6",
//	// "5", "Taps", "Taps info");
//	// assertEquals("Taps", subCategory.getName());
//	// assertEquals("Taps info", subCategory.getDesc().getVal());
//	//
//	// }
//	//
//	// @Test
//	// public void testdeleteSubCategorieById() {
//	// String subCategories = productCatalogService.deleteSubCategorieById(
//	// "123", "124");
//	// assertEquals(subCategories, "124");
//	// }
//	//
//	// @Test
//	// public void testGetProductType() throws Exception {
//	// List<ProductType> productType = productCatalogService.getProductType(
//	// "11", "22");
//	// assertTrue(productType.size() == 1);
//	// }
//	//
//	// @Test
//	// public void testCreateProductType() throws Exception {
//	// ProductType productType = productCatalogService.createProductType("6",
//	// "5", "Ivory1", "Ivory1 info");
//	// assertEquals("Ivory1", productType.getName());
//	// assertEquals("Ivory1 info", productType.getDesc().getVal());
//	// }
//	//
//	// @Test
//	// public void testUpdateProductType() throws Exception {
//	// ProductType productType = productCatalogService.updateProductType("12",
//	// "13", "6", "Ivory", "Ivory Info");
//	// assertEquals("Ivory", productType.getName());
//	// assertEquals("Ivory Info", productType.getDesc().getVal());
//	// }
//	//
//	// @Test
//	// public void testDeleteProductType() throws Exception {
//	// String prodtypId = productCatalogService.deleteProductType("12", "13",
//	// "6");
//	// assertEquals("6", prodtypId);
//	// }
//	//
//	// /*@Test
//	// public void testSearchProduct() throws Exception{
//	// List<Product>
//	// productList=productCatalogService.searchProduct(null,null,null,null,null,null,null,null,null,null,0,10,null);
//	//
//	// }*/
//	//
//	// @Test
//	// public void testCountProduct() throws Exception {
//	// long l =
//	// productCatalogService.getProductsCount(null,null,null,null,null,null,null,null,null,null);
//	// assertEquals(l, 4);
//	// }
//	// @Test
//	// public void testDeleteById() {
//	// String id1 = productCatalogService.deleteProduct("spfsda");
//	//
//	// assertEquals(id1, "spfsda");
//	// }
//	// @Test
//	// public void testSearchById() {
//	// Product product1 = productCatalogService.getProductBypid("12345");
//	// assertEquals(product1.getId(), "12345");
//	// }
//	// @Test
//	// public void testUpdateProduct() throws Exception {
//	// String pid = productCatalogService.updateProduct("1234", "abcdf",
//	// "ABCDF long name", "en,abcdf", "$,50000.0000", null, null,
//	// null, null, null, null, null, null, null);
//	// System.out.println(pid);
//	// assertEquals(pid, "1234");
//	//
//	// }
//	//
//	//
//	// /*@Test
//	// public void testCreateProduct() throws Exception {
//	//
//	// Product insertProduct = new Product();
//	// insertProduct.setSkuid("Jaq-bath-dadkda12345643");
//	// Set<SubCategory> subCategorySet = new HashSet<>();
//	// SubCategory subCatagory = new SubCategory();
//	// subCatagory.setName("abc");
//	// subCategorySet.add(subCatagory);
//	// insertProduct.setSubcategories(subCategorySet);
//	//
//	// Set<ProductType> productTypeSet = new HashSet<>();
//	// ProductType productType = new ProductType();
//	// productType.setName("ZYX");
//	// productTypeSet.add(productType);
//	// insertProduct.setProductTypes(productTypeSet);
//	//
//	// User user = new User();
//	// user.setUsername("asd@gmail.com");
//	//
//	// insertProduct.setProvider(user);
//	//
//	// // Product outProduct =
//	// // productCatalogService.createProduct(insertProduct);
//	// // assertEquals(outProduct.getSkuid(), "Jaq-bath-dadkda12345643");
//	//
//	// }*/
//	//
//	// // @Test
//	// // public void testFindCategory() {
//	// // List<Category> categoryList =
//	// productCatalogService.getAllCategories();
//	// // assertEquals(categoryList.size(), 3);
//	// // }
//	// //
//	// // @Test
//	// // public void testGetCategoryCount() {
//	// //
//	// // long l1 = productCatalogService.getCategoryCount();
//	// // assertEquals(10l, l1);
//	// //
//	// // long l2 = productCatalogService.getCategoryCount("Plumbing");
//	// // assertEquals(20l, l2);
//	// // }
//
//	@Test
//	public void testSearchDealers() throws Exception {
//		List<SellerInformation> dealers = productCatalogService.searchDealers("125", 0);
//		assertTrue(dealers.size() == 1);
//	}
//
//	@Test
//	public void testCountDealerForProduct() throws Exception {
//
//		long count = productCatalogService.countDealerForProduct("11");
//		assertEquals(10l, count);
//
//	}
//
//	/*
//	 * @Test public void testSearchProductByProviver() throws Exception { User
//	 * user = productCatalogService.searchProductByProvider(pid, userName,
//	 * fName, lName, email, pnum, roleName, fullName)("123");
//	 * assertEquals("123", user.getId()); }
//	 */
//
//	@Test
//	public void testGetRelatedProducts() throws Exception {
//		Set<Product> relatedProductIds = productCatalogService
//				.getRelatedProducts("123");
//		assertTrue(relatedProductIds.size() == 2);
//	}
//
//	@Test
//	public void testCreateRelatedProducts() throws Exception {
//		List<String> productIds = new ArrayList<>();
//		productIds.add("4");
//		productIds.add("5");
//		Set<String> relatedProducts = productCatalogService
//				.createRelatedProducts("125", productIds);
//		assertTrue(relatedProducts.size() == 4);
//	}
//
//	@Test
//	public void testUpdateRealatedProducts() throws Exception {
//
//		List<String> productIds = new ArrayList<>();
//		productIds.add("1");
//		productIds.add("2");
//		Set<String> relatedProducts = productCatalogService
//				.updateRelatedProducts("11", productIds);
//		assertTrue(relatedProducts.size() == 4);
//	}
//
//	@Test
//	public void testdeleteRelatedProducts() throws Exception {
//		List<String> productIds = new ArrayList<String>();
//		productIds.add("1");
//		productIds.add("2");
//		Set<String> relatedProductList = productCatalogService
//				.deleteRelatedProducts("125", productIds);
//		assertTrue(relatedProductList.size() == 2);
//	}
//
//	@Test
//	public void testGetRelatedAccsories() throws Exception {
//		Set<Product> relatedAccessories = productCatalogService
//				.getRelatedAccsories("22");
//		assertTrue(relatedAccessories.size() == 2);
//	}
//
//	@Test
//	public void testCreateRelatedAccessories() throws Exception {
//		List<String> accessoriesIds = new ArrayList<>();
//		accessoriesIds.add("123451");
//		accessoriesIds.add("123452");
//		Set<String> relatedAccessoriesIds = productCatalogService
//				.createRelatedAccessories("123", accessoriesIds);
//	}
//
//	@Test
//	public void testUpdateRelatedAccessories() throws Exception {
//		List<String> productIds = new ArrayList<String>();
//		productIds.add("7");
//		productIds.add("8");
//		Set<String> relatedProductList = productCatalogService
//				.updateRelatedAccessories("125", productIds);
//		assertTrue(relatedProductList.size() == 4);
//	}
//
//	/*
//	 * @Test public void testDeleteRelatedAccessories() { List<String>
//	 * accessoriesIdList = new ArrayList<String>();
//	 * accessoriesIdList.add("123"); Set<String> accessoriesIds =
//	 * productCatalogService .daleteRelatedAccessories("123",
//	 * accessoriesIdList); assertTrue(accessoriesIds.size() > 1);
//	 * 
//	 * }
//	 */
//}
