package com.zj.retrieval.cluster.actions.user;

import java.util.List;
import java.util.UUID;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.User;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.dao.UserDao;

public class AddUserAction {
	private String name;
	private String password;
	private String message;
	private String post_user_name;
	private String post_user_password;
	private boolean isError;
	
	public String getMessage() {
		return message;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String execute() {
		try {
			UserDao userDao = Util.getUserDao();
			if (!userDao.verifySu(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "�û������������";
				return ActionSupport.ERROR;
			}
			List<User> queryResult = userDao.getUserByName(name);
			if (queryResult.size() > 0) {
				this.isError = true;
				this.message = String.format("�Ѿ������û���Ϊ%1$s���û����뻻һ�����֡�", name);
				return ActionSupport.ERROR;
			} else {
				User user = new User();
				user.setId(UUID.randomUUID().toString());
				user.setName(name);
				user.setPassword(password);
				int result = userDao.addUser(user);
				if (result == 1) {
					this.isError = false;
					this.message = "��ӳɹ� o(��_��)o...";
					return ActionSupport.SUCCESS;
				} else {
					this.isError = true;
					this.message = "���ʧ��";
					return ActionSupport.ERROR;
				}
			}
		}
		catch (Exception e) {
			this.isError = true;
			this.message = e.getMessage();
			return ActionSupport.ERROR;
		}
	}
	public boolean getIsError() {
		return isError;
	}
	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}
	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
}
