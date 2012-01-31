package com.zj.retrieval.cluster.actions.node;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.NodeService;
import com.zj.retrieval.cluster.UserService;
import com.zj.retrieval.cluster.Util;

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
		UserService usrService = Util.getUserService();
		if (!usrService.verifySu(post_user_name, post_user_password)) {
			this.message = "Wrong user name or password.";
			this.isError = true;
			return ActionSupport.ERROR;
		}
		NodeService ns = Util.getNodeService();
		int result = ns.deleteNodeById(node_id);
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
