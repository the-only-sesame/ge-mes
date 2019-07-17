package com.mes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mes.beans.PageQuery;
import com.mes.beans.PageResult;
import com.mes.common.JsonData;
import com.mes.common.SameUrlData;
import com.mes.dto.ProductDto;
import com.mes.model.MesProduct;
import com.mes.param.MesProductVo;
import com.mes.param.SearchProductParam;
import com.mes.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static String FPATH="product";
	
	//待绑定钢材的页面跳转
		@RequestMapping("/productBind.page")
		public String productBindPage(String id,Model model) {
			//通过id拿到product的对象
			MesProduct mesProduct=productService.selectById(id);
			if(mesProduct!=null) {
				model.addAttribute("product",mesProduct);
				return FPATH+"/productBind";
			}else {
				return FPATH+"/productBindList";
			}
		}
	
	@RequestMapping("/productBindList")
	public String productBindList() {
		return FPATH+"/productBindList";
	}
	//绑定钢材分页显示
    @RequestMapping("/productBindList.json")
    @ResponseBody
	public JsonData searchBindListPage(SearchProductParam param, PageQuery page) {
		PageResult<ProductDto> pr=(PageResult<ProductDto>) productService.searchPageBindList(param, page);
		return JsonData.success(pr);
	}
	
	
	@Resource
	private ProductService productService;
	//钢锭查询界面显示
	@RequestMapping("/productIron")
	public String productIron() {
		return FPATH+"/productIron";
	}
	
	
	
	//倒库查询页面显示
	@RequestMapping("/productCome.page")
	public String productCome() {
		return FPATH+"/productCome";
	}
	//材料批量启动
	@RequestMapping("/productBatchStart.json")
	public String productBatchStart(String ids) {
	    productService.batchStart(ids);
		return FPATH+"/product";
	}
	
	
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
	
	
	//材料管理界面
	@RequestMapping("/product.page")
	public String productList() {
		return FPATH+"/product";
	}
	
	
	//材料分页模块
	@RequestMapping("/product.json")
	@ResponseBody
	public JsonData searchPage(SearchProductParam param, PageQuery page) {
		PageResult<ProductDto> pr=productService.searchPageList(param,page);
		return JsonData.success(pr);
	}
	
	
	
	
	
	
	
	
	
}
