package com.org.practicum.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.org.practicum.dao.OracleFactDAO;

@Component
public class OracleServiceController {
	private ApplicationContext daoContext = new ClassPathXmlApplicationContext("applicationContextDAO.xml");
	protected String loadCSV(String directoryPath){
		OracleFactDAO oracleFactDAO	= daoContext.getBean("oracleFactDAO", OracleFactDAO.class);
		String error = oracleFactDAO.loadCSV(directoryPath);
		return error;
		
	}

}
