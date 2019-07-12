package com.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mes.beans.PageQuery;
import com.mes.dto.SearchOrderDto;
import com.mes.model.MesOrder;



public interface MesOrderCustomerMapper {
	Long getOrderCount();

	int countBySearchDto(@Param("dto")SearchOrderDto dto);

	List<MesOrder> getPageListBySearchDto(@Param("dto")SearchOrderDto dto, @Param("page")PageQuery page);

}