package com.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlanController {
	private static String FPATH="plan/";
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
		}
	@RequestMapping("/planStarted.page")
	public String planStarted() {
		return FPATH+"planStarted";
	}
}
