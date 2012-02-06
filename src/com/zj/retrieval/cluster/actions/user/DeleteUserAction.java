package com.zj.retrieval.cluster.actions.user;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.UserDao;

public class DeleteUserAction {
	private String user_id;
	private String post_user_name;
	private String post_user_password;
	private String message;
	private boolean isError;
	public void setId(String id) {
		this.user_id = id;
	}
	public String execute() {
		try {
			UserDao us = Util.getUserDao();
			if (us.verifySu(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "用户名或密码错误";
				return ActionSupport.ERROR;
			}
			int result = us.removeUserById(user_id);
			if (result == 1) {
				this.isError = false;
				this.message = "删除成功 o(∩_∩)o...";
				return ActionSupport.SUCCESS;
			} else {
				this.isError = true;
				this.message = "删除失败";
				return ActionSupport.ERROR;
			}
		}
		catch (Exception e) {
			this.isError = true;
			this.message = e.getMessage();
			return ActionSupport.ERROR;
		}
	}
	public String getMessage() {
		return message;
	}
	public boolean getIsError() {
		return isError;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}
	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
}
