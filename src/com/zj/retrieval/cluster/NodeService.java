package com.zj.retrieval.cluster;

import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class NodeService {
	private SimpleJdbcTemplate sqlClient;
	private DataSource dataSource;
	private static Log log = LogFactory.getLog(NodeService.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sqlClient = new SimpleJdbcTemplate(dataSource);
	}
	
	public List<Node> queryAllNode() {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `owl`, `images` as imagesStr, `parent_name` as parentEnName from `node`";
		ParameterizedRowMapper<Node> rm = 
			ParameterizedBeanPropertyRowMapper.newInstance(Node.class);
		List<Node> queryResult = sqlClient.query(sql, rm);
		for (Node nd : queryResult) {
			try {
				Node.parseFromOWL(nd.getOwl(), nd);
			} catch (Exception e) {
				log.error("解析节点时发生错误，node.id = " + nd.getId(), e);
			}
		}
		return queryResult;	
	}

	public int addNode(Node nd) {
//		Util.fixDataSourceUrl(dataSource);
		nd.setOwl(Node.getOwlFromNode(nd));
		System.out.println("[OWL]: " + nd.getOwl());
		if (nd.getId() == null) nd.setId(UUID.randomUUID().toString());
		
		String sql = "insert into `node`(`id`, `name`, `owl`, `images`, `parent_name`) values(:id, :name, :owl, :imagesStr, :parentEnName)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(nd);
		int result = sqlClient.update(sql, param);
		return result;
	}

	public List<Node> queryNodeById(String id) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "select `id`, `name`, `owl`, `images` as imagesStr, `parent_name` as parentEnName " +
				"from `node` where `id`=?";
		ParameterizedRowMapper<Node> rm = 
			ParameterizedBeanPropertyRowMapper.newInstance(Node.class);
		List<Node> queryResult = sqlClient.query(sql, rm, id);
		for (Node nd : queryResult) {
			try {
				Node.parseFromOWL(nd.getOwl(), nd);
			} catch (Exception e) {
				log.error("解析节点时发生错误，node.id = " + nd.getId(), e);
			}
		}
		return queryResult;
	}

	public int deleteNodeById(String id) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "delete from `node` where `id`=?";
		int result = sqlClient.update(sql, id);
		return result;
	}

	public int updateNode(Node nd) {
//		Util.fixDataSourceUrl(dataSource);
		String sql = "update `node` set `id`=:id, `name`=:name, `owl`=:owl, `images`=:imagesStr where `id`=:id";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(nd);
		int result = sqlClient.update(sql, sps);
		return result;
	}
}
