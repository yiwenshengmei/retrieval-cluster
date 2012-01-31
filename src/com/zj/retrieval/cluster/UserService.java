package com.zj.retrieval.cluster;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class UserService {
	private SimpleJdbcTemplate sqlClient;
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sqlClient = new SimpleJdbcTemplate(dataSource);
	}
	public int addUser(User usr) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "insert into `user` values(:id, :name, :password, :authType)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(usr);
		return sqlClient.update(sql, param);
	}
	public List<User> queryAllUser() {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password`, `auth_type` as authType from `user`";
		ParameterizedBeanPropertyRowMapper<User> rm = 
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm);
	}
	public List<User> queryUserById(String id) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password`, `auth_type` as authType from `user` where `id`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, id);
	}
	public List<User> queryUserByName(String name) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `password`, `auth_type` as authType from `user` where `name`=?";
		ParameterizedBeanPropertyRowMapper<User> rm =
			ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		return sqlClient.query(sql, rm, name);
	}
	public int deleteById(String id) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "delete from `user` where `id`=?";
		return sqlClient.update(sql, id);
	}
	public int updateUser(User oldUser) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "update `user` set `id`=:id, `name`=:name, `password`=:password, `auth_type`=:authType where `id`=:id";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(oldUser);
		return sqlClient.update(sql, sps);
	}
	public boolean suVerify(String postUserName, String postUserPassword) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select count(*) from `su` where `name`=? and `password`=?";
		return sqlClient.queryForInt(sql, postUserName, postUserPassword) > 0;
	}
	
}
