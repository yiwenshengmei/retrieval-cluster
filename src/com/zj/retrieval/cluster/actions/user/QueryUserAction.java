package com.zj.retrieval.cluster.actions.user;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.User;
import com.zj.retrieval.cluster.UserService;
import com.zj.retrieval.cluster.Util;

public class QueryUserAction {
	private String id;
	private String name;
	private String password;
	private int auth_type;
	private String message;
	private List<User> queryResult;
	public List<User> getQueryResult() {
		return queryResult;
	}
	public String getMessage() {
		return message;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public int getAuth_type() {
		return auth_type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String execute() {
		UserService us = Util.getUserService();
		if (id.equals("*")) {
			List<User> users = us.queryAllUser();
			this.queryResult = users;
			this.message = convertUsers2JSON(users);
			return ActionSupport.SUCCESS;
		} else {
			this.queryResult = us.queryUserById(id);
			if (queryResult.size() != 0) {
				User user = queryResult.get(0);
				this.name = user.getName();
				this.password = user.getPassword();
				this.auth_type = user.getAuthType();
				return ActionSupport.SUCCESS;
			} else {
				return ActionSupport.ERROR;
			}
		}
	}
	public String convertUsers2JSON(List<User> users) {
		JSONArray result = new JSONArray();
		try {
			for (User user : users)
				result.put(new JSONObject(user.toJSONString()));
			return result.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
