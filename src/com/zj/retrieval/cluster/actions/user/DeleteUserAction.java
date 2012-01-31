package com.zj.retrieval.cluster.actions.user;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.UserService;
import com.zj.retrieval.cluster.Util;

public class DeleteUserAction {
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String execute() {
		UserService us = Util.getUserService();
		int result = us.deleteById(id);
		return result == 1 ? ActionSupport.SUCCESS : ActionSupport.ERROR;
	}
}
