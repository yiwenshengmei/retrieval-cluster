package com.zj.retrieval.cluster.actions.node;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.NodeService;
import com.zj.retrieval.cluster.UserService;
import com.zj.retrieval.cluster.Util;

public class QueryNodeAction {
	private String id;
	private String message;
	private List<Node> queryResult;
	public String getMessage() {
		return message;
	}
	public List<Node> getQueryResult() {
		return queryResult;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String execute() {
		NodeService ndService = Util.getNodeService();
		if (id.equals("*")) {
			this.queryResult = ndService.queryAllNode();
		} else {
			this.queryResult.add(ndService.queryNodeById(id));
		}
		return ActionSupport.SUCCESS;
	}

}
