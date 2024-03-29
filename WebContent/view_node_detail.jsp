<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="com.zj.retrieval.cluster.dao.NodeDao"
	import="com.zj.retrieval.cluster.Util"
	import="java.util.List"
	import="com.zj.retrieval.cluster.Node"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看详细信息</title>
</head>
<body>
<table width="95%" style='margin: 0 auto;'>
<%
	String node_id = request.getParameter("node_id");
	NodeDao dao = Util.getNodeDao();
	
	try {
		Node nd = dao.getNodeById(node_id);
		out.print("<tr><td style='width: 30%'></td><td style='width: 70%'></td></tr>");
		out.print(String.format("<tr><td>id: </td><td>%1$s</td></tr>", nd.getId()));
		out.print(String.format("<tr><td>name: </td><td>%1$s</td></tr>", nd.getName()));
		out.print(String.format("<tr><td>english name: </td><td>%1$s</td></tr>", nd.getEnglishName()));
		out.print(String.format("<tr><td>uri: </td><td>%1$s</td></tr>", nd.getUri()));
		out.print(String.format("<tr><td>uri name: </td><td>%1$s</td></tr>", nd.getUriName()));
		out.print(String.format("<tr><td>description: </td><td>%1$s</td></tr>", nd.getDesc()));
		out.print(String.format("<tr><td>label: </td><td>%1$s</td></tr>", nd.getLabel()));
		out.print(String.format("<tr><td>owl: </td><td>%1$s</td></tr>", Util.html(Util.formatXML(nd.getOwl(), 4))));
		out.print(String.format("<tr><td>english name of parent: </td><td>%1$s</td></tr>", nd.getParentEnglishName()));
		out.print("<tr><td colspan='2'>====== User Field ======</td></tr>");
		for (Entry<String, String> field : nd.getUserField().entrySet()) {
			out.print(String.format("<tr><td>%1$s</td><td>%2$s</td></tr>", field.getKey(), field.getValue()));
		}
		out.print("<tr><td colspan='2'>====== Images ======</td></tr>");
		for (String image_url : nd.getImages()) {
			out.print(String.format("<tr><td colspan='2'><img src='%1$s'/></td></tr>", image_url));
		}
	} catch (Exception ex) {
		out.print(ex.getMessage());
	}
%>
</table>
</body>
</html>