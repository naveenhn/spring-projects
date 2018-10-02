package com.sarvah.mbg.banners.service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.userprofile.response.BannerViewDetails;
import com.sarvah.mbg.userprofile.response.ManageBannersView;

public interface BannerService {
	/**
	 * 
	 * @param uid
	 * @return List<Banner>
	 * @throws Exception
	 */
	List<Banner> getUserBanners(String uid) throws Exception;

	/**
	 * 
	 * @param uid
	 * @return Long
	 * @throws Exception
	 */
	Long getUserBannerCount(String uid) throws Exception;

	/**
	 * 
	 * @param uid
	 * @param desc
	 * @param startDate
	 * @param enddate
	 * @param pids 
	 * @param eDate
	 * @return Banner
	 * @throws ParseException
	 * @throws Exception
	 */
	Banner createBanner(String uid, String name, String place,
			String startDate, String enddate, List<String> pids) throws ParseException, Exception;

	/**
	 * 
	 * @param uid
	 * @param banid
	 * @param desc
	 * @param startDate
	 * @param enddate
	 * @return Banner
	 * @throws ParseException
	 * @throws Exception
	 */
	Banner updateBanner(String uid, String banid, String desc,
			String startDate, String enddate) throws ParseException, Exception;

	/**
	 * 
	 * @param uid
	 * @param banid
	 * @return String
	 * @throws Exception
	 */
	String deleteUserBanner(String uid, String banid) throws Exception;

	String uploadFile(String bannerId, String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength) throws Exception;

	/**
	 * Method to get banners
	 * 
	 * @return
	 */
	List<Banner> getAllBanners();

	/**
	 * Method to get Admin manage banners list
	 * 
	 * @return
	 * @throws Exception
	 */
	List<ManageBannersView> getSuperAdminManageBanners() throws Exception;

	/**
	 * Method to get particular banner
	 * 
	 * @param banId
	 * @return
	 * @throws Exception
	 */
	Banner getBannerById(String banId) throws Exception;

	/**
	 * Method to get banner view details
	 * 
	 * @param banId
	 * @return
	 * @throws Exception
	 */
	BannerViewDetails getBannerViewDetails(String banId) throws Exception;

}
