package com.zj.retrieval.cluster.actions.node;

import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.NodeDao;
import com.zj.retrieval.cluster.dao.UserDao;

public class QueryNodeForRemoteAction {
	private String user_name;
	private String user_pwd;
	private String node_id;
	
	private String message;
	private boolean isError;
	
	public String execute() {
		try {
			UserDao usrService = Util.getUserDao();
			if (!usrService.verifyUser(user_name, user_pwd)) {
				this.message = "Wrong user name or password.";
				this.isError = true;
				return ActionSupport.ERROR;
			}
			
			NodeDao ndService = Util.getNodeDao();
			Node nd = ndService.getNodeById(node_id);
			JSONObject jNode = new JSONObject();
			
			processBaseInfo(jNode, nd);
			processUserField(jNode, nd);
			
			this.isError = false;
			this.message = jNode.toString(4);
			return ActionSupport.SUCCESS;
		} catch (Exception ex) {
			this.isError = true;
			this.message = ex.getMessage();
			return ActionSupport.ERROR;
		}
	}
	
	private void processBaseInfo(JSONObject jNode, Node nd) throws JSONException {
		jNode.put("name", nd.getName());
		jNode.put("name_en", nd.getEnName());
		jNode.put("author", nd.getAuthor());
		jNode.put("desc", nd.getDesc());
		jNode.put("uri", nd.getUri());
		jNode.put("uri_name", nd.getUriName());
		jNode.put("owl", Util.formatXML(nd.getOwl(), 4));
	}
	
	private void processUserField(JSONObject jNode, Node nd) throws JSONException {
		JSONArray jUserFields = new JSONArray();
		for (Entry<String, String> entry : nd.getUserField().entrySet()) {
			JSONObject jAttr = new JSONObject();
			jAttr.put("key", entry.getKey()).put("value", entry.getValue());
			jUserFields.put(jAttr);
		}
		jNode.put("user_field", jUserFields);
	}

	public String getMessage() {
		return message;
	}

	public boolean getIsError() {
		return isError;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
}
