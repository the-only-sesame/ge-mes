package com.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mes.beans.PageQuery;
import com.mes.dto.ProductDto;
import com.mes.dto.SearchProductDto;

public interface MesProductCustomerMapper {

	int countBySearchDto(@Param("dto")SearchProductDto dto);

	List<ProductDto> getPageListBySearchDto(@Param("dto")SearchProductDto dto, @Param("page")PageQuery page);

	Long getProductCount();

	int countBySearchBindListDto(@Param("dto")SearchProductDto dto);

	List<ProductDto> getPageListBySearchBindListDto(@Param("dto")SearchProductDto dto, @Param("page")PageQuery page);
   
}