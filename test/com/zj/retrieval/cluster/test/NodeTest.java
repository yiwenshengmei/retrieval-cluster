package com.zj.retrieval.cluster.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.NodeService;
import com.zj.retrieval.cluster.NodeType;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.actions.node.AddNodeAction;
import com.zj.retrieval.cluster.actions.node.QueryNodeAction;

public class NodeTest {
	
	public static ApplicationContext getApplicationContext() {
		return new FileSystemXmlApplicationContext("/WebContent/WEB-INF/beans.xml");
	}
	
	@Test
	public void addNodeTest() throws Exception {
		Util.applicationContext = getApplicationContext();
		AddNodeAction action = new AddNodeAction();
		
		action.setDesc("ThisIsDesc");
		action.setName_en("ThisIsEnglishName");
		action.setName("DaTouYu");
		action.setParent_uri("Thisisparenturi");
		action.setUri("http://xxx");
		action.setUri_name("Thisisuriname");
		action.setLabel("thisislabel");
		action.setUser_field("[{'key':'key0', 'value':'value0'}," +
				"{'key':'key1', 'value':'value1'}]");
		action.setParent_name_en("Thisisparentnameen");
				
		File[] images = new File[] {
			new File("D:/我的文档/My Pictures/人物/62e9d265gw1dol41mz20vj.jpg"),
			new File("D:/我的文档/My Pictures/人物/ac2fc3c45f7b349238db495f.jpg")	
		};
//		action.setImages(images);
		action.setImages(null);
		
		action.setPost_user_name("su");
		action.setPost_user_password("123");
		
		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
	}
	
	@Test
	public void queryAllNodeActionTest() {
		Util.applicationContext = getApplicationContext();
		QueryNodeAction action = new QueryNodeAction();
		action.setId("*");
		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
		List<Node> actionResult = action.getQueryResult();
		System.out.println(String.format("共%1$S记录数", actionResult.size()));
		for (Node nd : actionResult)
			System.out.println(nd);
	}
	
	@Test
	public void queryNodeByIdActionTest() {
		Util.applicationContext = getApplicationContext();
		String id = "8c65b1c2-fc3e-4609-b8cc-83626ab18c59";
		QueryNodeAction action = new QueryNodeAction();
		action.setId(id);
		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
		List<Node> actionResult = action.getQueryResult();
		System.out.println("返回记录数: " + actionResult.size());
		assertTrue(actionResult.size() == 1);
		Node nd = actionResult.get(0);
		System.out.println(nd);
		assertTrue(nd.getName().equals("DaTouYu"));
	}
	
	@Test
	public void deleteNodeTest() {
		String id = "e7774a77-4b1e-4c74-9486-bb40ca19afac";
		ApplicationContext ctx = getApplicationContext();
		NodeService ns = (NodeService) ctx.getBean("nodeService");
		int result = ns.deleteNodeById(id);
		System.out.println("result: " + result);
		assertTrue(result == 1);
	}
	
	@Test
	public void updateNodeTest() {
		String newName = "LiYu+3";
		String id = "acf32-444-5555-s-abdc";
		ApplicationContext ctx = getApplicationContext();
		NodeService ns = (NodeService) ctx.getBean("nodeService");
		Node before = ns.queryNodeById(id).get(0);
		before.setName(newName);
		int result = ns.updateNode(before);
		System.out.println("result: " + result);
		assertTrue(result == 1);
		Node after = ns.queryNodeById(id).get(0);
		System.out.println("Name: " + after.getName());
		assertTrue(after.getName().equals(newName));
	}
	
	@Test
	public void codetest() {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
	}
}
