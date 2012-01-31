package com.zj.retrieval.cluster;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
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

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Node {
	private String id;
	private String desc;
	private String enName;
	private Integer nodeType;
	private String uri;
	private String uriName;
	private Map<String, String> userField;
	private String name;
	private String owl;
	private String parentURI;
	private String label;
	private String parentEnName;
	private static Log log = LogFactory.getLog(Node.class);
	public String getParentEnName() {
		return parentEnName;
	}
	public void setParentEnName(String parentEnName) {
		this.parentEnName = parentEnName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getParentURI() {
		return parentURI;
	}
	public void setParentURI(String parentURI) {
		this.parentURI = parentURI;
	}
	public static void parseFromOWL(String owl, Node node) throws Exception {
		try {
			
			if (owl == null || owl.equals("")) {
				node.setDesc("");
				node.setLabel("");
				node.setUserField(new HashMap<String, String>());
				return;
			}
			
			XMLBuilder builder = XMLBuilder.parse(new InputSource(new StringReader(node.getOwl())));
			
			// ����uri, uriname
			String uriName = builder.xpathFind(String.format("/RDF/%1$s", node.getParentEnName())).getElement().getAttribute("rdf:ID");
			String uri = uriName.split("#")[0];
			
			// ����name_en
			String[] uri_name_splited = uriName.split("#");
			String name_en = uri_name_splited.length > 1 ? uri_name_splited[1] : "no_name_en";
			
			// ����userfields
			Map<String, String> userfields = new HashMap<String, String>();
			try {
				NodeList nodeFields = builder.xpathFind(
						String.format("/RDF/%1$s/userfields", node.getParentEnName())
				).getElement().getChildNodes();
				for (int i = 0; i < nodeFields.getLength(); i++) {
					String key = ((Element) nodeFields.item(i)).getAttribute("key");
					String value = nodeFields.item(i).getTextContent();
					userfields.put(key, value);
				}
			} catch (XPathExpressionException e) {
				log.info(String.format("�����ڽڵ�/RDF/%1$s/userfields, userField������Ϊ��", node.getParentEnName()));
			}
			
			// ����label
			String label = "";
			try {
				label = builder.xpathFind(
						String.format("/RDF/%1$s/label", node.getParentEnName())
				).getElement().getTextContent();
			} catch (Exception e1) {
				log.info(String.format("�����ڽڵ�/RDF/%1$s/label, label������Ϊ��", node.getParentEnName()));
			}
			
			// ����Desc
			String desc = "";
			try {
				desc = builder.xpathFind(
						String.format("/RDF/%1$s/desc", node.getParentEnName())
				).getElement().getTextContent();
			} catch(XPathExpressionException e) {
				log.info(String.format("�����ڽڵ�/RDF/%1$s/desc, desc������Ϊ��", node.getParentEnName()));
			}
			
			node.setDesc(desc);
			node.setUriName(uriName);
			node.setUri(uri);
			node.setLabel(label);
			node.setUserField(userfields);
			node.setEnName(name_en);
		} catch (Exception e) {
			log.error("����OWLʱ����", e);
			throw new Exception("����OWLʱ����@NodeService.parseFromOWL()", e);
		}
	}
	
	public static String getOwlFromNode(Node nd) {

		String result = null;
		try {
			
			XMLBuilder builder = XMLBuilder.create("rdf:RDF");
			
			builder.a("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			builder.a("xmlns:owl", "http://www.w3.org/2002/07/owl#");
			builder.a("xmlns:rdfs", "http://www.w3.org/2000/01/rdf-schema#");
			builder.a("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");
			
			// ����individual���ͽڵ��owl��ʽ
			XMLBuilder individual = builder.e(nd.getParentEnName());
			individual.a("rdf:ID", nd.getUri() + "#" + nd.getEnName())
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
			throw new RuntimeException("����OWL�ַ���ʱ����", ex);
		}
		return result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
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
		String[] splited = str.split(";");
		images = Arrays.asList(splited);
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", desc=" + desc + ", enName=" + enName
				+ ", nodeType=" + nodeType + ", uri=" + uri + ", uriName="
				+ uriName + ", userField=" + userField + ", name=" + name
				+ ", owl=" + owl.substring(0, 10) + "..." + ", parentURI=" + parentURI + ", label="
				+ label + ", parentEnName=" + parentEnName
				+ ", getImagesStr()=" + getImagesStr() + "]";
	}


	
}
