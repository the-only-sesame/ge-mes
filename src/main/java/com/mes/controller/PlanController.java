package com.mes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mes.beans.PageQuery;
import com.mes.beans.PageResult;
import com.mes.common.JsonData;
import com.mes.model.MesPlan;
import com.mes.param.SearchPlanParam;
import com.mes.service.PlanService;



@Controller
@RequestMapping("/plan")
public class PlanController {
	private static String FPATH="plan/";
	@Resource
	private PlanService planService;
	
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
		}
	@RequestMapping("/planStarted.page")
	public String planStarted() {
		return FPATH+"planStarted";
	}
	
	
	//批量处理待执行计划启动
		//批量启动处理
		@ResponseBody
		@RequestMapping("/planBatchStart.json")
		public JsonData planBatchStart(String ids) {
			planService.batchStartWithIds(ids);
			return JsonData.success();
		}
	
	
	//分页显示
    @RequestMapping("/plan.json")
    @ResponseBody
    public JsonData searchPage(SearchPlanParam param, PageQuery page) {
    	PageResult<MesPlan> pr=(PageResult<MesPlan>) planService.searchPageList(param, page);
    	return JsonData.success(pr);
    }
}
