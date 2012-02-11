<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="common.css" type="text/css" rel="stylesheet" />
<title>添加用户</title>
</head>
<body>
<form action='user/add' method="post">
	name: <input name='name' type="text"/>
	password: <input name='password' type='text'/>
	post_user_name: <input name='post_user_name' type='text'/>
	post_user_password: <input name='post_user_password' type='text'/>
	<input type='submit' name='submit' value='添加'/>
</form>
</body>
</html>