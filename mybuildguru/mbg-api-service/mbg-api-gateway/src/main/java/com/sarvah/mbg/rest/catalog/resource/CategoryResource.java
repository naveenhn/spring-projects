/**
 * 
 */
package com.sarvah.mbg.rest.catalog.resource;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.catalog.service.ProductCategoryService;
import com.sarvah.mbg.catalog.service.model.CategorySummary;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.rest.catalog.model.CategoryCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.CategoryUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductTypeCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductTypeUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.SubCategoryCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.SubCategoryUpdateRequestParam;
import com.sarvah.mbg.rest.exception.MBGAppException;

/**
 * @author naveen
 *
 */
@Component
@Path("/catalog/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * method for searching the categories
	 * 
	 * @param name
	 * @return
	 * @throws MBGAppException
	 */

	@GET
	public Response searchCategories(@QueryParam("name") String name,
			@QueryParam("view") String view,
			@QueryParam("productType") String productType)
			throws MBGAppException {
		Response response = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("summary")) {
				List<CategorySummary> summaryCategories = productCategoryService
						.getCategoriesSummary(productType);
				response = Response.ok(summaryCategories).build();
			} else {
				List<Category> categories = productCategoryService
						.searchCategories(name);
				response = Response.ok(categories).build();
			}

		} catch (Exception e) {
			throw new MBGAppException("Error occured during Categories search",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);

		}
		return response;
	}

	/**
	 * Method to get count of category
	 * 
	 * @param name
	 * @return
	 */
	@GET
	@Path("/count")
	public Response getCategoryCount(@QueryParam("name") String name) {

		Long count = productCategoryService.getCategoryCount(name);

		return Response.ok(count).build();

	}

	/**
	 * method for create category.
	 * 
	 * @param categoryCreateRequestParam
	 * @return
	 */
	@POST
	public Response createCategory(
			CategoryCreateRequestParam categoryCreateRequestParam) {
		String name = categoryCreateRequestParam.getName();
		String desc = categoryCreateRequestParam.getDesc();
		Category category = null;
		try {
			category = productCategoryService.createCategory(name, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(category).build();
	}

	/**
	 * method for searching the categories based on categoryId.
	 * 
	 * @paramcatid
	 * @return
	 * @throwsMBGAppException
	 */
	@GET
	@Path("/{catid}")
	public Response searchCategoriesById(@PathParam("catid") String cateid)
			throws MBGAppException {
		try {
			Category categorie = productCategoryService
					.searchCategoriesById(cateid);
			return Response.ok(categorie).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search the categorie", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for update category based on categoryId.
	 * 
	 * @param catid
	 * @param categoryUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/{catid}")
	public Response updateCategory(@PathParam("catid") String catid,
			CategoryUpdateRequestParam categoryUpdateRequestParam) {
		String name = categoryUpdateRequestParam.getName();
		String desc = categoryUpdateRequestParam.getDesc();
		Category category = null;
		try {
			category = productCategoryService.updateCategory(catid, name, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(category).build();
	}

	/**
	 * Method to delete a category
	 * 
	 * @param catId
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/{catid}")
	public Response deleteCategory(@PathParam("catid") String catId)
			throws Exception {

		catId = productCategoryService.deleteCategory(catId);

		return Response.ok(catId).build();
	}

	/**
	 * method for get the Subcategories based on categoryId.
	 * 
	 * @paramsubcat
	 * @return
	 * @throwsMBGAppException
	 */
	@GET
	@Path("/{catid}/subcat")
	public Response searchSubCategories(@PathParam("catid") String cateid,
			@QueryParam("view") String view) throws MBGAppException {
		Response response = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("categorysummary")) {
				CategorySummary categorySummary = productCategoryService
						.getCategorySummary(cateid);
				response = Response.ok(categorySummary).build();
			} else {
				Set<SubCategory> subCategories = productCategoryService
						.searchSubCategories(cateid);
				response = Response.ok(subCategories).build();
			}
			return response;
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search the subCategories", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to create a new subCategory
	 * 
	 * @param catId
	 * @param subCategoryCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/{catid}/subcat")
	public Response createSubCategory(@PathParam("catid") String catId,
			SubCategoryCreateRequestParam subCategoryCreateRequestParam)
			throws Exception {

		String desc = subCategoryCreateRequestParam.getDesc();
		String subCatName = subCategoryCreateRequestParam.getSubCatName();

		SubCategory subCategory = productCategoryService.createSubCategory(
				catId, desc, subCatName);
		return Response.ok(subCategory).build();
	}

	/**
	 * method for get the subcategory based on subcategoryId.
	 * 
	 * @paramcatid
	 * @PathParamsubcatid
	 * @return
	 * @throwsMBGAppException
	 */

	@GET
	@Path("/{catid}/subcat/{subcatid}")
	public Response searchSubCategorieById(@PathParam("catid") String cateid,
			@PathParam("subcatid") String subcatid) throws MBGAppException {
		SubCategory subCategories = null;
		try {
			subCategories = productCategoryService.searchSubCategorieById(
					cateid, subcatid);
			return Response.ok(subCategories).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to searching the subCategories", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for update subCategory based on subcategoryId.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param subCategoryUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/{catid}/subcat/{subcatid}")
	public Response updateSubCategory(@PathParam("catid") String catid,
			@PathParam("subcatid") String subcatid,
			SubCategoryUpdateRequestParam subCategoryUpdateRequestParam) {
		String name = subCategoryUpdateRequestParam.getName();
		String desc = subCategoryUpdateRequestParam.getDesc();
		SubCategory subCategory = null;
		try {
			subCategory = productCategoryService.updateSubCategory(catid,
					subcatid, name, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(subCategory).build();
	}

	/**
	 * method for Deleteing the subcategory
	 * 
	 * @paramcatid
	 * @PathParamsubcatid
	 * @return
	 * @throwsMBGAppException
	 */
	@DELETE
	@Path("/{catid}/subcat/{subcatid}")
	public Response deleteSubCategorieById(@PathParam("catid") String cateid,
			@PathParam("subcatid") String subcatid) throws MBGAppException {
		try {
			String subCategories = productCategoryService
					.deleteSubCategorieById(cateid, subcatid);
			return Response.ok(subCategories).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Deleteing the subCategories", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get product type
	 * 
	 * @param catId
	 * @param subcatId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{catid}/subcat/{subcatid}/prodtype")
	public Response getProductType(@PathParam("catid") String catId,
			@PathParam("subcatid") String subcatId) throws Exception {
		List<ProductType> productTypes = productCategoryService.getProductType(
				catId, subcatId);
		return Response.ok(productTypes).build();
	}

	/**
	 * Method for create ProductType
	 * 
	 * @param catid
	 * @param subcatid
	 * @param productTypeCreateRequestParam
	 * @return
	 */
	@POST
	@Path("/{catid}/subcat/{subcatid}/prodtype")
	public Response createProductType(@PathParam("catid") String catid,
			@PathParam("subcatid") String subcatid,
			ProductTypeCreateRequestParam productTypeCreateRequestParam) {

		String name = productTypeCreateRequestParam.getName();
		String desc = productTypeCreateRequestParam.getDesc();
		ProductType productType = null;
		try {
			productType = productCategoryService.createProductType(catid,
					subcatid, name, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(productType).build();
	}

	/**
	 * method for update ProductType.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypid
	 * @param productTypeUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/{catid}/subcat/{subcatid}/prodtype/{prodtypid}")
	public Response updateProductType(@PathParam("catid") String catid,
			@PathParam("subcatid") String subcatid,
			@PathParam("prodtypid") String prodtypid,
			ProductTypeUpdateRequestParam productTypeUpdateRequestParam) {
		String name = productTypeUpdateRequestParam.getName();
		String desc = productTypeUpdateRequestParam.getDesc();
		ProductType productType = null;
		try {
			productType = productCategoryService.updateProductType(catid,
					subcatid, prodtypid, name, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(productType).build();
	}

	/**
	 * method for delete ProductType.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypid
	 * @return
	 */
	@DELETE
	@Path("/{catid}/subcat/{subcatid}/prodtype/{prodtypid}")
	public Response deleteProductType(@PathParam("catid") String catid,
			@PathParam("subcatid") String subcatid,
			@PathParam("prodtypid") String prodtypid) {
		String prodtypeId = null;
		try {
			prodtypeId = productCategoryService.deleteProductType(catid,
					subcatid, prodtypid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(prodtypeId).build();
	}
}
