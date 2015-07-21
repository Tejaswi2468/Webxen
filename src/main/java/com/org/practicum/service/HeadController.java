package com.org.practicum.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.practicum.dao.OracleFactDAO;

@Controller
// @RequestMapping("")
public class HeadController {

	@RequestMapping("")

	public ModelAndView helloWorld() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("msg", "Welcome To Webxen!");
		return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginForm() {
		
		ModelAndView modelAndView = new ModelAndView("actionChoice");
		modelAndView.addObject("msg", "Welcome");
		return modelAndView;
	}
	
	@RequestMapping(value = "/csvForm", method = RequestMethod.GET)
	public ModelAndView getCsvForm() {
		ModelAndView modelAndView = new ModelAndView("csvForm");
		modelAndView.addObject("msg", "Welcome");
		return modelAndView;
	}
	
	@RequestMapping(value = "/generateCsv", method = RequestMethod.POST)
	public ModelAndView postCsvForm() {
		System.out.println("inside generate csv");
		ModelAndView modelAndView = new ModelAndView("csvLoad");
		
		//tada
		
		modelAndView.addObject("msg", "Your files have succesfully been loaded.");
		return modelAndView;
	}
}