package com.zj.retrieval.cluster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.zj.retrieval.cluster.User;
import com.zj.retrieval.cluster.Util;

public class UserDao {
	private SimpleJdbcTemplate sqlClient;
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sqlClient = new SimpleJdbcTemplate(dataSource);
	}
	public int addUser(User usr) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "insert into `user` values(:id, :name, :password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(usr);
		return sqlClient.update(sql, param);
	}
	public List<User> getAllUser() {
		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password` from `user`";
		ParameterizedBeanPropertyRowMapper<User> rm = 
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm);
	}
	public List<User> getUserById(String id) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password` from `user` where `id`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, id);
	}
	public List<User> getUserByName(String name) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password` from `user` where `name`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, name);
	}
	public int removeUserById(String id) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "delete from `user` where `id`=?";
		return sqlClient.update(sql, id);
	}
	public int updateUser(User oldUser) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "update `user` set `id`=:id, `name`=:name, `password`=:password where `id`=:id";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(oldUser);
		return sqlClient.update(sql, sps);
	}
	public boolean verifySu(String postUserName, String postUserPassword) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "select count(*) from `su` where `name`=? and `password`=?";
		return sqlClient.queryForInt(sql, postUserName, postUserPassword) > 0;
	}
	public boolean verifyUser(String name, String password) {
		Util.fixDataSourceUrl(dataSource);
		String sql = "select count(*) form `user` where `name`=? and `password`=?";
		return sqlClient.queryForInt(sql, name, password) > 0;
	}
	
}
