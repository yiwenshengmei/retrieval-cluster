package com.zj.retrieval.cluster;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String id;
	private String name;
	private String password;
	public User() {
		
	}
	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
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
	public String toJSONString() {
		JSONObject user = new JSONObject();
		try {
			user.put("id", this.id);
			user.put("name", this.name);
			user.put("password", this.password);
			return user.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}	
}
