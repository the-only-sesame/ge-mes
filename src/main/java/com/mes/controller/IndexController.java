package com.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/admin.page")
	public String adminPage() {
		return "admin";
	}
	
	@RequestMapping("/dept.page")
	public String deptPage() {
		return "dept";
	}
	
	
}
