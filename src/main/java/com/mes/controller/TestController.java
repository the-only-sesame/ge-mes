package com.mes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mes.model.TestDemo;
import com.mes.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Resource
	private TestService testService;

	@RequestMapping("/index.page")
	public String test() {
		TestDemo test=new TestDemo();
		test.setId(1);
		test.setName("a01");
		testService.persist(test);
		return "test";
	}
	
}
