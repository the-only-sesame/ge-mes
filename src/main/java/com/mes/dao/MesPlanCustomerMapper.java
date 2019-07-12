package com.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mes.beans.PageQuery;
import com.mes.dto.SearchPlanDto;
import com.mes.model.MesPlan;



public interface MesPlanCustomerMapper {
	Long getPlanCount();

	// @Param("dto")--给mapper.xml查询sql指定参数名称 #{dto.keyword}
	int countBySearchDto(@Param("dto") SearchPlanDto dto);

	List<MesPlan> getPageListBySearchDto(@Param("dto") SearchPlanDto dto, @Param("page") PageQuery page);

	void batchStartWithIds(@Param("list") String[] tempIds);

}