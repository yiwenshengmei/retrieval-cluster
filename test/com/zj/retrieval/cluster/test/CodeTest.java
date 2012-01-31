package com.zj.retrieval.cluster.test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.zj.retrieval.cluster.Node;

public class CodeTest {
	@Test
	public void uriTest() throws JSONException, URISyntaxException {
		URL url = Node.class.getResource("/");
		File f = new File(url.toURI());
		System.out.println(f);
	}
}
