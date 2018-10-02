/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.userprofile.model.BrandCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.BrandUpdateRequestParam;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.service.UserBrandService;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserBrandResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private UserBrandService userService;

	/**
	 * method for get Provider brands.
	 * 
	 * @param uid
	 * @return
	 */
	@GET
	@Path("/{uid}/brands")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getProviderBrands(@PathParam("uid") String uid,
			@Context SecurityContext securityContext) {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		ProductBrand productBrand = null;
		try {
			productBrand = userService.getProviderBrands(uid);
		} catch (Exception e) {
			logger.info("Error occured during getting brands");
			throw new MBGAppException("Error occured during getting brands", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productBrand).build();
	}

	/**
	 * method for create brand.
	 * 
	 * @param uid
	 * @param brandCreateRequestParam
	 * @return
	 */
	@POST
	@Path("/{uid}/brands")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createBrand(@PathParam("uid") String uid,
			BrandCreateRequestParam brandCreateRequestParam,
			@Context SecurityContext securityContext) {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String name = brandCreateRequestParam.getName();
		String desc = brandCreateRequestParam.getDesc();
		ProductBrand productBrand = null;
		try {
			productBrand = userService.createBrand(uid, name, desc);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during creating brand", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productBrand).build();
	}

	/**
	 * method for update product brand.
	 * 
	 * @param uid
	 * @param brandid
	 * @param brandUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/{uid}/brands/{brandid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateBrand(@PathParam("uid") String uid,
			@PathParam("brandid") String brandid,
			BrandUpdateRequestParam brandUpdateRequestParam,
			@Context SecurityContext securityContext) {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String name = brandUpdateRequestParam.getName();
		String desc = brandUpdateRequestParam.getDesc();
		Set<String> subCatIds = brandUpdateRequestParam.getSubCatIds();
		ProductBrand productBrand = null;
		try {
			productBrand = userService.updateBrand(uid, brandid, name, desc,
					subCatIds);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during updating brand", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productBrand).build();
	}

	/**
	 * method for delete brand
	 * 
	 * @param uid
	 * @param brandid
	 * @return
	 */
	@DELETE
	@Path("/{uid}/brands/{brandid}")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteBrand(@PathParam("uid") String uid,
			@PathParam("brandid") String brandid,
			@Context SecurityContext securityContext) {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		ProductBrand productBrand = null;
		try {
			productBrand = userService.deleteBrand(uid, brandid);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during brand delete", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productBrand).build();
	}

	protected static ApiUser getApiUserFromSecurityContext(
			SecurityContext securityContext) {

		if (securityContext != null) {

			ApiUser apiUser = (ApiUser) securityContext.getUserPrincipal();
			return apiUser;

		}
		return null;
	}
}
