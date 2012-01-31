package com.zj.retrieval.cluster.actions.user;

import java.util.List;
import java.util.UUID;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.User;
import com.zj.retrieval.cluster.UserService;
import com.zj.retrieval.cluster.Util;

public class AddUserAction {
	private String name;
	private String password;
	private Integer auth_type;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAuth_type(Integer auth_type) {
		this.auth_type = auth_type;
	}
	public String execute() {
		UserService us = Util.getUserService();
		List<User> queryResult = us.queryUserByName(name);
		if (queryResult.size() > 0) {
			this.message = String.format("已经存在用户名为%1$s的用户，请换一个名字。", name);
			return ActionSupport.ERROR;
		} else {
			User user = new User();
			user.setId(UUID.randomUUID().toString());
			user.setName(name);
			user.setPassword(password);
			user.setAuthType(auth_type);
			int result = us.addUser(user);
			return result == 1 ? ActionSupport.SUCCESS : ActionSupport.ERROR;
		}
	}
}
