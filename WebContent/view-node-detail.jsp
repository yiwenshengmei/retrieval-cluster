<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="com.zj.retrieval.cluster.NodeService"
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
	NodeService ns = Util.getNodeService();
	
	Node nd = ns.queryNodeById(node_id);
	if (nd == null) {
		out.print("不存在编号为：" + node_id + " 的物种。");
	} else {
		out.print(String.format("<tr><td>id: </td><td>%1$s</td></tr>", nd.getId()));
		out.print(String.format("<tr><td>name: </td><td>%1$s</td></tr>", nd.getName()));
		out.print(String.format("<tr><td>english name: </td><td>%1$s</td></tr>", nd.getEnName()));
		out.print(String.format("<tr><td>uri: </td><td>%1$s</td></tr>", nd.getUri()));
		out.print(String.format("<tr><td>uri name: </td><td>%1$s</td></tr>", nd.getUriName()));
		out.print(String.format("<tr><td>description: </td><td>%1$s</td></tr>", nd.getDesc()));
		out.print(String.format("<tr><td>label: </td><td>%1$s</td></tr>", nd.getLabel()));
		out.print(String.format("<tr><td>owl: </td><td>%1$s</td></tr>", Util.html(Util.formatXML(nd.getOwl(), 4))));
		out.print(String.format("<tr><td>english name of parent: </td><td>%1$s</td></tr>", nd.getParentEnName()));
		out.print(String.format("<tr><td>uri of parent: </td><td>%1$s</td></tr>", nd.getParentURI()));
		out.print("<tr><td colspan='2'>====== User Field ======</td></tr>");
		for (Entry<String, String> field : nd.getUserField().entrySet()) {
	out.print(String.format("<tr><td>%1$s</td><td>%2$s</td></tr>", field.getKey(), field.getValue()));
		}
		out.print("<tr><td colspan='2'>====== Images ======</td></tr>");
		for (String image_url : nd.getImages()) {
	out.print(String.format("<tr><td colspan='2'><img src='%1$s'/></td></tr>", image_url));
		}
	}
%>
</table>
</body>
</html>