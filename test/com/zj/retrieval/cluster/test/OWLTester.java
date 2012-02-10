package com.zj.retrieval.cluster.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zj.retrieval.cluster.Node;
import com.zj.retrieval.cluster.NodeType;

public class OWLTester {
	
	@Test
	public void testOowl() {
		Node nd = new Node();
		nd.setDesc("ThisIsDesc");
		nd.setEnglishName("ThisIsEnglishName");
		nd.setName("DaTouYu");
		nd.setNodeType(NodeType.NODETYPE_INDIVIDUAL);
		nd.setUri("http://xxx");
		nd.setUriName("Thisisuriname");
		nd.setLabel("thisislabel");
		
		List<String> images = new ArrayList<String>();
		images.add("r34842384902384.jpg");
		images.add("x34255969dfsddd.jpg");
		nd.setImages(images);
		
		Map<String, String> user_field_mapper = new HashMap<String, String>();
		user_field_mapper.put("diy_name", "jzhao");
		user_field_mapper.put("diy_age", "14");
		
		nd.setUserField(user_field_mapper);
		nd.setParentEnglishName("Thisisparentnameen");
		
		String owl = Node.getOWLFromNode(nd);
		System.out.println(owl);
	}
}
