<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>添加物种</title>
	<style type="text/css">
		.no_value_input {
			border-color: red;
		}
	</style>
	<script type="text/javascript" src="jquery-1.7.1.js"></script>
	<script type="text/javascript">
	var user_field_index = 0;
	var image_index = 0;

	// 删除一条用户自定义属性
	function delete_user_field(index) {
		$('#user_field_' + index).remove();
	}

	// 删除一张图片的上传field
	function delete_image_field(index) {
		$('#image_field_' + index).remove();
	}

	$(function() {
		
		// 增加一条用户自定义属性
		$("#add_user_field").click(function() {
			var location = $("#add_user_field_location");
			var new_field = $(
				"<div id='user_field_" + user_field_index + "'>" +
					"<span>" +
						"key: <input id='user_field_key_" + user_field_index + "' type='text'/>" +
					"</span>" + 
					"<span>" +
						"value: <input id='user_field_value_" + user_field_index + "' type='text'>" +
					"</span>" +
					"<span>" +
						"<a class='user_field_delete' href='#' onclick='delete_user_field(" + user_field_index + ");'>DELETE</a>" +
					"</span>" +
				"</div>"
			);
			location.append(new_field);
			user_field_index++;
		});
		
		// 增加一张图片
		$("#add_image").click(function() {
			var location = $('#add_image_location');
			var new_file_field = $(
				"<div id='image_field_" + image_index + "'>" +
					"<input type='file' name='images' accept='image/jpeg'/>" +
					"<span>" +
						"<a href='#' onclick='delete_image_field(" + image_index + ");'>DELETE</a>" +
					"</span>" +
				"</div>"
				);
				location.append(new_file_field);
				image_index++;
		});
		
		// 拦截表单的提交，为隐藏域填充值
		$("#submit_form").click(function() {
			
			// 检验表单
			var has_no_value_input = false;
			$('input:text').each(function() {
				if ($(this).val() == '') {
					has_no_value_input = true;
					$(this).addClass('no_value_input');
				} else {
					$(this).removeClass('no_value_input');
				}
			});
			if (has_no_value_input) {
				alert('数据未填写完整。');
				return false;
			}
			
			var user_fields_json = '[ ';
			
			for (var i = 0; i < user_field_index; i++) {
				var field_key_id   = 'user_field_key_'   + i;
				var field_value_id = 'user_field_value_' + i;
				
				if ($('#' + field_key_id).length == 0) {
					if (i == (user_field_index - 1)) 
						user_fields_json = user_fields_json.substr(0, user_fields_json.length - 1);
					continue;
				}
				var key = $('#' + field_key_id).val();
				var value = $('#' + field_value_id).val();
				
				var one_field = '{key: "' + key + '", value: "' + value + '"}';
				user_fields_json += (i == (user_field_index - 1) ? one_field : one_field + ',');
			}
			user_fields_json += ' ]';
			$('#user_field').val(user_fields_json);
			// 提交表单
			$('#add_node_form').submit();
			return false;
		});



	}); // end of $(function() {
	</script>
</head>
<body>
	<form id="add_node_form" action="node/add" method="post" enctype="multipart/form-data" >
		<table id="base_info">
			<tr><td>node name: </td><td><input name="name" type="text"/></td></tr>
			<tr><td>node name en: </td><td><input name="name_en" type="text"/></td></tr>
			<tr><td>node desc: </td><td><input name="desc" type="text"/></td></tr>
			<tr><td>uri:</td><td><input name="uri" type="text"/></td></tr>
			<tr><td>uri name: </td><td><input name="uri_name" type="text"/></td></tr>
			<tr><td>label: </td><td><input name="label" type="text"/></td></tr>
			<tr><td>parent uri: </td><td><input name="parent_uri"/></td></tr>
			<tr><td>parent name en: </td><td><input name="parent_name_en"/></td></tr>
		</table>
		
		<a id="add_image" href="#">Add Image</a>
		<div id="add_image_location">
			<!--
			<div id='image_value_x'>
				<input type='file' accept='image/jpeg'/>
				<span>
					<a href='#' onclick='delete_image_field(x)'>DELETE</a>
				</span>
			</div>
			-->
		</div>
		
		
		<a id="add_user_field" href="#">Add Your Field</a>
		<div id="add_user_field_location">
			<!--
				<div id='user_field_32'>
					<span>
						key: <input id='user_field_key_32' type='text'/>
					</span>
					<span>
						value: <input id='user_field_value_32' type='text'>
					</span>
					<span>
						<a class='user_field_delete' href='#' onclick='delete_user_field(32);'>DELETE</a>
					</span>
				</div>
			-->
		</div>
		
		
		<input id='user_field' type="hidden" name="user_field"/>
		PostUserName: <input type='text' name='post_user_name'/>
		PostUserPassword: <input type='text' name='post_user_password'/>
		<div><a id="submit_form" href="#">SUBMIT</a></div>
	</form>
</body>
</html>