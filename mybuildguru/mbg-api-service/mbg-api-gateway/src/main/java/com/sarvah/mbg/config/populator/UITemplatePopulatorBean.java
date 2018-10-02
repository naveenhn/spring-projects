/**
 * 
 */
package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.UITemplate;
import com.sarvah.mbg.userprofile.dao.mongo.BannerUITemplateDAO;

/**
 * @author shivu s
 *
 */
public class UITemplatePopulatorBean {
	BannerUITemplateDAO bannerUITemplateDAO;

	UITemplatePopulatorBean(BannerUITemplateDAO bannerUITemplateDAO) {
		this.bannerUITemplateDAO = bannerUITemplateDAO;
	}

	public void initUITemplate() {
		bannerUITemplateDAO.deleteAll();
//		UITemplate uitemplate = new UITemplate();
//		uitemplate.setName("Ganesh Chaturthi Offer");
//		Description desc = new Description();
//		desc.setLang("eng");
//		desc.setVal("Ganesh Chaturthi Offer Information");
//		uitemplate.setDesc(desc);
//		bannerUITemplateDAO.insert(uitemplate);
//
//		UITemplate uitemplate1 = new UITemplate();
//		uitemplate1.setName("Sunady Offer");
//		Description desc1 = new Description();
//		desc1.setLang("eng");
//		desc1.setVal("Sunday Offer Information");
//		uitemplate.setDesc(desc1);
//		bannerUITemplateDAO.insert(uitemplate1);

	}
}
