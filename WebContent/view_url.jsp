<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看本机访问URL</title>
</head>
<body>
<h1>本机访问URL为: <%=request.getLocalAddr() + ":" + request.getLocalPort() + "/" + application.getServletContextName() + application.getContextPath()%></h1>
</body>
</html>