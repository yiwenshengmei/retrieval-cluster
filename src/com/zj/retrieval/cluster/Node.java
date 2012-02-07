package com.zj.retrieval.cluster;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jamesmurty.utils.XMLBuilder;

public class Node {
	private String id;
	private String author;
	private String desc;
	private String nameEN;
	private Integer nodeType;
	private String uri;
	private String uriName;
	private Map<String, String> userField;
	private String name;
	private String owl;
	private String label;
	private String parentEnglishName;
	private static Log log = LogFactory.getLog(Node.class);
	public String getParentEnglishName() {
		return parentEnglishName;
	}
	public void setParentEnglishName(String parentEnglishName) {
		this.parentEnglishName = parentEnglishName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public static void parseNodeFromOWL(String owl, Node node) throws Exception {
		try {
			
			if (owl == null || owl.equals("")) {
				node.setDesc("");
				node.setLabel("");
				node.setUserField(new HashMap<String, String>());
				return;
			}
			
			XMLBuilder builder = XMLBuilder.parse(new InputSource(new StringReader(node.getOwl())));
			
			// 解析uri, uriname
			String uriName = builder.xpathFind(String.format("/RDF/%1$s", node.getParentEnglishName())).getElement().getAttribute("rdf:ID");
			String uri = uriName.split("#")[0];
			
			// 解析name_en
			String[] uri_name_splited = uriName.split("#");
			String name_en = uri_name_splited.length > 1 ? uri_name_splited[1] : "no_name_en";
			
			// 解析userfields
			Map<String, String> userfields = new HashMap<String, String>();
			try {
				NodeList nodeFields = builder.xpathFind(
						String.format("/RDF/%1$s/userfields", node.getParentEnglishName())
				).getElement().getChildNodes();
				for (int i = 0; i < nodeFields.getLength(); i++) {
					String key = ((Element) nodeFields.item(i)).getAttribute("key");
					String value = nodeFields.item(i).getTextContent();
					userfields.put(key, value);
				}
			} catch (XPathExpressionException e) {
				log.info(String.format("不存在节点/RDF/%1$s/userfields, userField将保持为空", node.getParentEnglishName()));
			}
			
			// 解析label
			String label = "";
			try {
				label = builder.xpathFind(
						String.format("/RDF/%1$s/label", node.getParentEnglishName())
				).getElement().getTextContent();
			} catch (Exception e1) {
				log.info(String.format("不存在节点/RDF/%1$s/label, label将保持为空", node.getParentEnglishName()));
			}
			
			// 解析Desc
			String desc = "";
			try {
				desc = builder.xpathFind(
						String.format("/RDF/%1$s/desc", node.getParentEnglishName())
				).getElement().getTextContent();
			} catch(XPathExpressionException e) {
				log.info(String.format("不存在节点/RDF/%1$s/desc, desc将保持为空", node.getParentEnglishName()));
			}
			
			node.setDesc(desc);
			node.setUriName(uriName);
			node.setUri(uri);
			node.setLabel(label);
			node.setUserField(userfields);
			node.setEnglishName(name_en);
		} catch (Exception e) {
			log.error("解析OWL时出错", e);
			throw new Exception("解析OWL时出错@NodeService.parseFromOWL()", e);
		}
	}
	
	public static String getOWLFromNode(Node nd) {

		String result = null;
		try {
			
			XMLBuilder builder = XMLBuilder.create("rdf:RDF");
			
			builder.a("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			builder.a("xmlns:owl", "http://www.w3.org/2002/07/owl#");
			builder.a("xmlns:rdfs", "http://www.w3.org/2000/01/rdf-schema#");
			builder.a("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");
			
			// 创建individual类型节点的owl格式
			XMLBuilder individual = builder.e(nd.getParentEnglishName());
			individual.a("rdf:ID", nd.getUri() + "#" + nd.getEnglishName())
				.e("label").t(nd.getLabel()).up()
				.e("name").t(nd.getName()).up()
				.e("desc").t(nd.getDesc());
			
			XMLBuilder eImages = individual.e("images");
			for (String image_path : nd.getImages()) {
				eImages.e("item").t(image_path);
			}
//				.e("image").t(nd.getImage());
			
			// Create <userfields>
			XMLBuilder eUserFields = individual.e("userfields");
			Map<String, String> userfields = nd.getUserField();
			for (Entry<String, String> entry : userfields.entrySet()) {
				eUserFields.e("field").a("key", entry.getKey()).t(entry.getValue());
			}
			
			result = builder.asString();
			
		} catch (Exception ex) {
			throw new RuntimeException("构建OWL字符串时出错", ex);
		}
		return result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEnglishName() {
		return nameEN;
	}
	public void setEnglishName(String enName) {
		this.nameEN = enName;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUriName() {
		return uriName;
	}
	public void setUriName(String uriName) {
		this.uriName = uriName;
	}
	public Map<String, String> getUserField() {
		return userField;
	}
	public void setUserField(Map<String, String> userField) {
		this.userField = userField;
	}
	private List<String> images;
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
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
	public String getOwl() {
		return owl;
	}
	public void setOwl(String owl) {
		this.owl = owl;
	}
	public String getImagesStr() {
		if (images == null || images.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for (String image : images) {
			sb.append(image + ";");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}
	public void setImagesStr(String str) {
		if (str == null || str.isEmpty()) {
			images = new ArrayList<String>();
		} else {
			String[] splited = str.split(";");
			images = Arrays.asList(splited);
		}
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
