package com.zj.retrieval.cluster.actions.node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.NodeType;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.NodeDao;
import com.zj.retrieval.cluster.dao.UserDao;

public class AddNodeAction {
	private String name;
	private String name_en;
	private String desc;
	private String uri;
	private String uri_name;
	private String user_field;
	private String parent_uri;
	private String label;
	private String parent_name_en;
	private File[] images;
	private String post_user_name;
	private String post_user_password;
	private String message;
	private String node_id;
	
	public String getNode_id() {
		return this.node_id;
	}
	public void setParent_name_en(String parent_name_en) {
		this.parent_name_en = parent_name_en;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setParent_uri(String parent_uri) {
		this.parent_uri = parent_uri;
	}
	
	public void setImages(File[] images) {
		this.images = images;
	}

	public void setPost_user_name(String postUserName) {
		this.post_user_name = postUserName;
	}

	public void setPost_user_password(String postUserPassword) {
		this.post_user_password = postUserPassword;
	}


	public String execute() {
		try {
			UserDao usrService = Util.getUserDao();
			if (!usrService.verifySu(post_user_name, post_user_password)) {
				this.message = "Wrong user name or password.";
				return ActionSupport.ERROR;
			}
			NodeDao ndService = Util.getNodeDao();
			
			Node nd = new Node();
			nd.setDesc(desc);
			nd.setEnName(name_en);
			nd.setParentURI(parent_uri);
			nd.setParentEnName(parent_name_en);
			
			List<String> imagelist = new ArrayList<String>();
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
//			String realpath = "E:/retrieval/retrieval-cluster/WebContent/images";
			File folder = new File(realpath);
			if(!folder.exists()) folder.mkdirs();
			if (null != images) {
				for (File srcfile : images) {
					String filename = UUID.randomUUID().toString() + ".jpg";
					File destfile = new File(folder, filename);
					FileUtils.copyFile(srcfile, destfile);
					imagelist.add("images/" + filename);
				}
			}
			nd.setImages(imagelist);
			nd.setName(name);
			nd.setNodeType(NodeType.NODETYPE_INDIVIDUAL);
			nd.setUri(uri);
			nd.setUriName(uri_name);
			nd.setLabel(label);
			
			if (user_field != null && !user_field.equals("")) {
				JSONArray jarray = new JSONArray(user_field);
				Map<String, String> userFieldMap = new HashMap<String, String>();
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject j = jarray.getJSONObject(i);
					userFieldMap.put(j.getString("key"), j.getString("value"));
				}
				nd.setUserField(userFieldMap);
			}

			int result = ndService.addNode(nd);
			this.node_id = nd.getId();
			
			if (result == 1) {
				this.message = String.format("成功添加物种[ID=%1$s]", this.node_id);
				return ActionSupport.SUCCESS;
			} else {
				this.message = "failed";
				return ActionSupport.ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.message = "execute方法发生错误";
			return ActionSupport.ERROR;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUri_name(String uri_name) {
		this.uri_name = uri_name;
	}

	public void setUser_field(String user_field) {
		this.user_field = user_field;
	}

}
