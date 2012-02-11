<%@page import="com.zj.retrieval.cluster.User"%>
<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.cluster.Util"%>
<%@page import="com.zj.retrieval.cluster.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="common.css" type="text/css" rel="stylesheet" />
<title>查询用户</title>
<script type="text/javascript" src="jquery-1.7.1.js"></script>
<script type="text/javascript">
	function delete_user(id) {
		if ($('#post_user_name').val().length == 0 || $('#post_user_password').val().length == 0) {
			alert('请首先输入管理员用户名和密码。');
			return false;
		}
		$.ajax({
			type: 'post',
			url: 'user/delete',
			success: function (data, textStatus, jqXHR) {
				if (data.isError) {
					alert("没有删除成功: " + data.message);
				} else {
					alert("删除成功: " + data.message);
					$('#' + id).hide('slow', function() {
						$('#' + id).remove();
					});
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				alert('ajax error: ' + textStatus);
			},
			data: { 'user_id': id, 'post_user_name': $('#post_user_name').val(), 'post_user_password': $('#post_user_password').val() },
			dataType: 'json'
		});
		return false;
	}
</script>
</head>
<body>
<%
UserDao dao = Util.getUserDao();
List<User> users = dao.getAllUser();
%>
<div style='width: 600px;margin: 0 auto;'>
	UserID: <input id='post_user_name' type="text" name='post_user_id'/>
	UserPassword: <input id='post_user_password' type='password' name='post_user_password'>
</div>
<table style='width: 600px;margin: 0 auto;'>
	<tr><td>ID</td><td>Name</td><td></td></tr>
	<% for (User user : users) { %>
	<tr id='<%=user.getId()%>'><td><%=user.getId() %></td><td><%=user.getName() %></td><td><a href='#' onclick='delete_user("<%=user.getId()%>");'>DELETE</a></td></tr>
	<% } %>
	
</table>
</body>
</html>