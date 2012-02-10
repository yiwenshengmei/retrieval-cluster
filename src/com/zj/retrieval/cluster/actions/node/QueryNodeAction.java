package com.zj.retrieval.cluster.actions.node;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.NodeDao;

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
		NodeDao ndService = Util.getNodeDao();
		if (id.equals("*")) {
			this.queryResult = ndService.getAllNode();
		} else {
			this.queryResult.add(ndService.getNodeById(id));
		}
		return ActionSupport.SUCCESS;
	}

}
