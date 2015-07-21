package com.org.practicum.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	// naach banadriya
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;// = new JdbcTemplate();
	private NamedParameterJdbcTemplate nameParaJdbcTemp;
	private static final String commaDelimiter = ",";
	private static final String newLineSeparator = "\n";
	/*
	 * it is deprecated to declare both kinds of templates. hence use simple
	 * jdbc template. used for 1.5 or above jdks. par ye to idhar ulta dikha
	 * raha hai....kya fart hai bc!
	 */
	private SimpleJdbcTemplate smpleJdbcTemp;

	public String loadCSV(String directoryPath) {
		List<String> a = this.fetchDIM();
		this.columnNamesCSV(a);
		return null;
	}

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

	private List<String> fetchDIM() {
		String sql = "select table_name from user_tables";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		List<String> dimTables = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			String s = (String) m.get("TABLE_NAME");
			if (s.contains("DIM")) {
				dimTables.add(s);
			}
		}
		System.out.println(dimTables.size());
		for (int j = 0; j < dimTables.size(); j++)
			System.out.println(dimTables.get(j));
		return dimTables;
	}

	private boolean columnNamesCSV(List<String> dimTables) {
		for (int i = 0; i < dimTables.size(); i++) {
			String sql = "select COLUMN_NAME from user_tab_columns where table_name='" + dimTables.get(i) + "'";
			// String sql="select * from "+dimTables.get(i);
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

			List<String> columnNames = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> m = list.get(j);
				String s = (String) m.get("COLUMN_NAME");
				columnNames.add(s);

			}
			// System.out.println(i+" "+list.get(0));
			boolean random = createCSV(columnNames, dimTables.get(i));
		}
		return false;
	}

	private boolean createCSV(List<String> columnNames, String tableName)// directory
																			// path
	{
		StringBuilder builder = new StringBuilder();
		for (int k = 0; k < columnNames.size() - 1; k++) {
			builder.append(columnNames.get(k));
			builder.append(",");
		}
		builder.append(columnNames.get(columnNames.size() - 1));
		String fileheader = builder.toString();

		String sql = "Select * from " + tableName;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		FileWriter fileWriter = null;
		String fileName = "D:\\" + tableName + ".csv";
		try {
			fileWriter = new FileWriter(fileName);
			// Write the CSV file header
			fileWriter.append(fileheader);
			// Add a new line separator after the header
			fileWriter.append(newLineSeparator);
			System.out.println(tableName + "list" + list.size());
			// Write a new student object list to the CSV file
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> m = list.get(j);
				System.out.println(tableName + "m" + m.size());
				for (String cn : columnNames) {
					Object obj = (Object) m.get(cn);
					System.out.println("obj : " + obj);
					String s = "";

					if (obj != null) {
						s = obj.toString();
					}
					System.out.println("s : " + s);

					/*
					 * String s1=String.valueOf(obj); System.out.println("s : "
					 * +s1);
					 */
					fileWriter.append(s);

					fileWriter.append(commaDelimiter);

				}
				fileWriter.append(newLineSeparator);

			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

		return false;
	}

}
