package com.org.practicum.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OracleFactDAO {
//naach banadriya
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;// = new JdbcTemplate();
	private NamedParameterJdbcTemplate nameParaJdbcTemp;

	/*
	 * it is deprecated to declare both kinds of templates. hence use simple jdbc
	 * template. used for 1.5 or above jdks. par ye to idhar ulta dikha raha hai....kya fart hai bc!
	 */
	private SimpleJdbcTemplate smpleJdbcTemp;
	
	
	public NamedParameterJdbcTemplate getNameParaJdbcTemp() {
		return nameParaJdbcTemp;
	}

	public void setNameParaJdbcTemp(NamedParameterJdbcTemplate nameParaJdbcTemp) {
		this.nameParaJdbcTemp = nameParaJdbcTemp;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		// normally, you use only either jdbc temp or named para jdbc temp. but
		// since the coding is only an example, let both remain.
		this.nameParaJdbcTemp = new NamedParameterJdbcTemplate(dataSource);
	}
    public void fetchDIM()
    {
    	String sql="select table_name from user_tables";
    	List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		System.out.println(list.get(0));
		
    }
}
