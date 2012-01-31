package com.zj.retrieval.cluster;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String id;
	private String name;
	private String password;
	private int authType;
	public User() {
		
	}
	public User(String id, String name, String password, int authType) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.authType = authType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAuthType() {
		return authType;
	}
	public void setAuthType(int authType) {
		this.authType = authType;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", authType=" + authType + "]";
	}
	public String toJSONString() {
		JSONObject user = new JSONObject();
		try {
			user.put("id", this.id);
			user.put("name", this.name);
			user.put("password", this.password);
			user.put("auth_type", this.authType);
			return user.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}	
}
