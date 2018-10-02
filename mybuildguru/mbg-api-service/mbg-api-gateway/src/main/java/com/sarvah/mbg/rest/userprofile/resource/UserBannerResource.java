/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.banners.service.BannerService;
import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.rest.catalog.model.BannerCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.BannerUpdateRequestParam;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.userprofile.response.BannerViewDetails;
import com.sarvah.mbg.userprofile.response.ManageBannersView;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserBannerResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private BannerService bannerService;

	/**
	 * Method to get all banners
	 * 
	 * @param view
	 * @return
	 */
	@GET
	@Path("/banners")
	public Response getAllBanners(@QueryParam("view") String view) {

		List<Banner> banners = new ArrayList<>();
		List<ManageBannersView> manageBannersView = new ArrayList<>();
		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AdminManageBanners")) {
			try {
				manageBannersView = bannerService.getSuperAdminManageBanners();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(manageBannersView).build();
		}
		banners = bannerService.getAllBanners();

		return Response.ok(banners).build();
	}

	/**
	 * Method to get particular banner
	 * 
	 * @param banId
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/banners/{banid}")
	public Response getBannerById(@PathParam("banid") String banId,
			@QueryParam("view") String view) throws Exception {

		Banner banner = new Banner();
		BannerViewDetails bannerViewDetails = new BannerViewDetails();
		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AdminBannerViewDetails")) {
			bannerViewDetails = bannerService.getBannerViewDetails(banId);
			return Response.ok(bannerViewDetails).build();
		}
		try {
			banner = bannerService.getBannerById(banId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(banner).build();
	}

	/**
	 * Method to get all the banners for the particular user
	 * 
	 * @Pathparam would be the userID
	 * 
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/banners")
	public Response getUserBanners(@PathParam("uid") String uid)
			throws MBGAppException {
		List<Banner> banner = null;
		try {
			banner = bannerService.getUserBanners(uid);
		} catch (Exception e) {
			logger.info("unable to find user Banner for the product");
			throw new MBGAppException(
					"unable to find user Banner for the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(banner).build();
	}

	/**
	 * Method to get count of all the banners for the particular user
	 * 
	 * @Pathparam would be the userID
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/banners/count")
	public Response getUserBannersCount(@PathParam("uid") String uid)
			throws Exception {

		Long count = null;
		try {
			count = bannerService.getUserBannerCount(uid);
		} catch (Exception e) {
			logger.info("unable to count user Banner for the product");
			throw new MBGAppException(
					"unable to count user Banner for the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(count).build();
	}

	/**
	 * method for create banner.
	 * 
	 * @param uid
	 * @param bannerCreateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{uid}/banners")
	public Response createBanner(@PathParam("uid") String uid,
			BannerCreateRequestParam bannerCreateRequestParam)
			throws ParseException, MBGAppException {

		String name = bannerCreateRequestParam.getName();
		String place = bannerCreateRequestParam.getPlace();
		String sDate = bannerCreateRequestParam.getStartDate();
		String eDate = bannerCreateRequestParam.getEndDate();
		List<String> pids = bannerCreateRequestParam.getProIds();

		Banner banner = null;
		try {
			banner = bannerService.createBanner(uid, name, place, sDate, eDate,
					pids);
		} catch (Exception e) {
			logger.info("unable to create Banner");
			throw new MBGAppException("unable to create Banner", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(banner).build();
	}

	/**
	 * Method for update banner.
	 * 
	 * @param uid
	 * @param banid
	 * @param bannerUpdateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/banners/{banid}")
	public Response updateBanner(@PathParam("uid") String uid,
			@PathParam("banid") String banid,
			BannerUpdateRequestParam bannerUpdateRequestParam)
			throws ParseException, MBGAppException {
		String desc = bannerUpdateRequestParam.getDesc();
		String sDate = bannerUpdateRequestParam.getStartDate();
		String eDate = bannerUpdateRequestParam.getEndDate();
		Banner banner = null;
		try {
			banner = bannerService.updateBanner(uid, banid, desc, sDate, eDate);
		} catch (Exception e) {
			logger.info("unable to update Banner for the product");
			throw new MBGAppException(
					"unable to update Banner for the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(banner).build();

	}

	/**
	 * Method to delete a particular banner of the particular user
	 * 
	 * @PathParam would be the userId and bannerId
	 * 
	 * @param uid
	 * @param banid
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{uid}/banners/{banid}")
	public Response deleteUserBanner(@PathParam("uid") String uid,
			@PathParam("banid") String banid) throws MBGAppException {

		String bannerid = null;
		try {
			bannerid = bannerService.deleteUserBanner(uid, banid);
		} catch (Exception e) {
			throw new MBGAppException("unable to delete banner", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(bannerid).build();
	}

}
