package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.role.Action;
import com.sarvah.mbg.userprofile.dao.mongo.ActionDAO;

public class ActionPopulatorBean {
	ActionDAO actionDAO;

	public ActionPopulatorBean(ActionDAO actionDAO) {
		this.actionDAO = actionDAO;

	}

	public void initAction() {

		actionDAO.deleteAll();

		Action actionToAdd = new Action();
		actionToAdd.setName("Add");
		Description addDesc = new Description("eng", "Action To Add");
		actionToAdd.setDesc(addDesc);

		actionDAO.insert(actionToAdd);

	}
}
