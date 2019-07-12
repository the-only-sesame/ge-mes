package com.mes.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mes.beans.PageQuery;
import com.mes.beans.PageResult;
import com.mes.common.JsonData;
import com.mes.model.MesOrder;
import com.mes.param.MesOrderVo;
import com.mes.param.SearchOrderParam;
import com.mes.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static String FPATH="order/";
	
	@Resource
	private OrderService orderService;
	
	@RequestMapping("/orderBatch.page")
	public String orderBatchPage() {
		return FPATH+"orderBatch";
	}
	
	@RequestMapping("/order.page")
	public String orderPage() {
		return FPATH+"order";
	}
	
	
	@ResponseBody
	@RequestMapping("/order.json")
	public JsonData searchPage(SearchOrderParam param,PageQuery page) {
		PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param, page);
    	return JsonData.success(pr);
	}
	
	
	//添加接收json数据的注解
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesOrderVo mesOrderVo) {
		orderService.orderBatchInserts(mesOrderVo);
		return JsonData.success();
	}
	
	//更新操作
	@ResponseBody
	@RequestMapping("/update.json")
	public JsonData updateOrder(MesOrderVo mesOrderVo) {
		orderService.update(mesOrderVo);
		return JsonData.success();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


















