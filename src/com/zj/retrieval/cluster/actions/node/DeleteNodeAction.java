package com.zj.retrieval.cluster.actions.node;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.NodeDao;
import com.zj.retrieval.cluster.dao.UserDao;

public class DeleteNodeAction {
	private String node_id;
	private String message;
	private boolean isError;
	private String post_user_name;
	private String post_user_password;
	
	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}

	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}

	public void setNode_id(String id) {
		this.node_id = id;
	}
	
	public boolean getIsError() {
		return this.isError;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String execute() {
		UserDao userDao = Util.getUserDao();
		if (!userDao.verifySu(post_user_name, post_user_password)) {
			this.message = "Wrong user name or password.";
			this.isError = true;
			return ActionSupport.ERROR;
		}
		NodeDao ns = Util.getNodeDao();
		int result = ns.removeNodeById(node_id);
		if (result != 1) {
			this.message = "Sorry, Something wrong, %>_<%";
			this.isError = true;
			return ActionSupport.ERROR;
		} else {
			this.message = String.format("Success, o(∩_∩)o...", node_id);
			this.isError = false;
			return ActionSupport.SUCCESS;
		}
	}
}
