package com.mes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mes.common.SameUrlData;
import com.mes.param.MesProductVo;
import com.mes.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static String FPATH="product";
	
	@Resource
	private ProductService productService;
	
	//增加材料页面显示
	@RequestMapping("/productInsert.page")
	public String productInsertPage() {
		return FPATH+"/productinsert";
	}
	
	
	//增加材料功能
	@RequestMapping("/insert.json")
	@SameUrlData//防止重复提交
	public String insert (MesProductVo productVo) {
		productService.insert(productVo);
		return FPATH+"/product";
	}
	
	
}
