//package com.zj.retrieval.cluster.actions.user;
//
//import java.util.List;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.zj.retrieval.cluster.User;
//import com.zj.retrieval.cluster.Util;
//import com.zj.retrieval.cluster.dao.UserDao;
//
//public class UpdateUserAction {
//	private String id;
//	private String name;
//	private String password;
//	private int auth_type;
//	private String message;
//	public String getMessage() {
//		return message;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public void setAuth_type(int auth_type) {
//		this.auth_type = auth_type;
//	}
//	public String execute() {
//		UserDao us = Util.getUserDao();
//		List<User> queryResult = us.getUserByName(name);
//		if (queryResult.size() > 0) {
//			this.message = String.format("�Ѿ������û���Ϊ%1$s���û����뻻һ�����֡�", name);
//			return ActionSupport.ERROR;
//		} else {
//			User user = new User(id, name, password, auth_type);
//			int result = us.updateUser(user);
//			if (result != 1) {
//				this.message = "����û�ʱ��������: " + user;
//				return ActionSupport.ERROR;
//			} else {
//				return ActionSupport.SUCCESS;
//			}
//		}
//		
//	}
//}
