package com.zj.retrieval.cluster;

import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zj.retrieval.cluster.dao.NodeDao;
import com.zj.retrieval.cluster.dao.UserDao;

public class Util {
	private static boolean dbPathHasFixed = false;
	public static ApplicationContext applicationContext = null;
	
	public static void fixDataSourceUrl(DataSource dataSource) {
		if (dbPathHasFixed) return;
		BasicDataSource basicDataSource = (BasicDataSource)dataSource;
		String dbName = basicDataSource.getUrl().split(":")[2];
		System.out.println("数据库文件名为: " + dbName);
		String dbfilepath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/" + dbName);
		String realDbURL = "jdbc:sqlite://" + dbfilepath;
		System.out.println("数据库Url为: " + realDbURL);
		basicDataSource.setUrl(realDbURL);
		dbPathHasFixed = true;
	}
	
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			ServletContext servletCtx = ServletActionContext.getServletContext();
			WebApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
			return springCtx;
		} else {
			return applicationContext;
		}
	}
	
	public static UserDao getUserDao() {
		ApplicationContext ctx = getApplicationContext();
		return (UserDao) ctx.getBean("userDao");
	}
	
	public static NodeDao getNodeDao() {
		return (NodeDao) getApplicationContext().getBean("nodeDao");
	}
	
	public static String html(String content) {
		if(content==null) return "";        
	    String html = content;
	    html = html.replaceAll("'", "&apos;");
	    html = html.replaceAll("\"", "&quot;");
	    html = html.replaceAll("\t", "&nbsp;&nbsp;");
	    html = html.replaceAll(" ", "&nbsp;");
	    html = html.replaceAll("<", "&lt;");
	    html = html.replaceAll(">", "&gt;");
	    html = html.replaceAll("\n", "<br/>");
//	    html = html.replaceAll("&", "&amp;");
	    return html;
	}
	
	public static String formatXML(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
