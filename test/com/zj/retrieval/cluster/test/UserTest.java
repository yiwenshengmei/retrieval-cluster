package com.zj.retrieval.cluster.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.cluster.User;
import com.zj.retrieval.cluster.Util;
import com.zj.retrieval.cluster.actions.user.AddUserAction;
import com.zj.retrieval.cluster.actions.user.DeleteUserAction;
import com.zj.retrieval.cluster.dao.UserDao;

public class UserTest {
	@Test
	public void addUserTest() {
		Util.applicationContext = getApplicationContext();
		AddUserAction action = new AddUserAction();
		action.setName("XiaoXue++");
		action.setPassword("XiaoXue'sPWD");
//		action.setAuth_type(AuthorityType.FULL);
		String actionResult = action.execute();
		if (!actionResult.equals(ActionSupport.SUCCESS)) System.out.println(action.getMessage());
		assertTrue(actionResult.equals(ActionSupport.SUCCESS));
	}
	
	@Test
	public void suVerifyTest() {
		UserDao userService = getUserService();
		assertTrue(userService.verifySu("su", "123"));
		assertFalse(userService.verifySu("su1", "123"));
		assertFalse(userService.verifySu("su", "1234"));
	}
	
//	@Test
//	public void queryAllUserTest() {
//		Util.applicationContext = getApplicationContext();
//		QueryUserAction action = new QueryUserAction();
//		action.setId("*");
//		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
//		System.out.println(action.getMessage());
//		List<User> users = action.getQueryResult();
//		for (User usr : users) {
//			System.out.println(usr);
//		}
//	}
	
//	@Test
//	public void queryUserActionTest() {
//		QueryUserAction action = new QueryUserAction();
//		action.setId("*");
//		String result = action.execute();
//		assertTrue(result.equals(ActionSupport.SUCCESS));
//		System.out.println(action.getMessage());
//	}
	
	public static ApplicationContext getApplicationContext() {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("/WebContent/WEB-INF/beans.xml");
		return ctx;
	}
	public static UserDao getUserService() {
		ApplicationContext ctx = getApplicationContext();
		UserDao us = (UserDao) ctx.getBean("userService");
		return us;
	}
	
//	@Test
//	public void queryUserTest() {
//		Util.applicationContext = getApplicationContext();
//		QueryUserAction action = new QueryUserAction();
//		String id = "6f73fdeb-3579-4433-ac69-43fa2d92c54e";
//		action.setId(id);
//		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
//		List<User> users = action.getQueryResult();
//		assertTrue(users.size() == 1);
//		User usr = users.get(0);
//		assertTrue(usr.getId().equals(id));
//		System.out.println(usr);
//	}
	
	@Test
	public void deleteUserTest() {
		Util.applicationContext = getApplicationContext();
		DeleteUserAction action = new DeleteUserAction();
		String id = "6f73fdeb-3579-4433-ac69-43fa2d92c54e";
		action.setId(id);
		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
	}
	
	@Test
	public void queryByNameTest() {
		String name = "XiaoXue+";
		UserDao us = getUserService();
		List<User> queryResult = us.getUserByName(name);
		assertTrue(queryResult.size() > 0);
		System.out.println(queryResult.get(0));
		assertTrue(queryResult.get(0).getName().equals(name));
	}
	
//	@Test
//	public void updateUserTest() {
//		Util.applicationContext = getApplicationContext();
//		String id = "8577b193-defc-48be-a828-6d5f2f1668e8";
//		String newName = "XiaoXue++";
//		
//		UpdateUserAction action = new UpdateUserAction();
//		QueryUserAction query_action = new QueryUserAction();
//
//		query_action.setId(id);
//		query_action.execute();
//		User oldUser = query_action.getQueryResult().get(0);
//		
//		System.out.println("Old user's name: " + oldUser.getName());
//		oldUser.setName(newName);
//		oldUser.setAuthType(AuthorityType.BRIEF);
//		
//		action.setId(oldUser.getId());
//		action.setName(oldUser.getName());
//		action.setPassword(oldUser.getName());
//		action.setAuth_type(oldUser.getAuthType());
//		
//		assertTrue(action.execute().equals(ActionSupport.SUCCESS));
//		
//		query_action.execute();
//		User new_user = query_action.getQueryResult().get(0);
//		assertTrue(new_user.getName().equals(newName));
//		assertTrue(new_user.getAuthType() == AuthorityType.BRIEF);
//	}
}
