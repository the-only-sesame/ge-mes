package com.mes.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.mes.dao.MesProductMapper;
import com.mes.model.MesProduct;
import com.mes.param.MesProductVo;
import com.mes.util.BeanValidator;
import com.mes.util.UUIDUtil;

@Service
public class ProductService {

	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private MesProductMapper mesProductMapper;
	
	
	
	//增加材料模块
	public void insert(MesProductVo productVo) {
		//校验
		BeanValidator.check(productVo);
		//首先需要获取到需要增加的个数
		Integer counts=productVo.getCounts();
		if (counts != null && counts > 0) {
			for (int i = 0; i < counts; i++) {
				//批量增加-productDto
				MesProduct pd = MesProduct.builder().productId(UUIDUtil.generateUUID())//
						.productTargetweight(productVo.getProductTargetweight())//
						.productRealweight(productVo.getProductRealweight())//
						.productLeftweight(productVo.getProductLeftweight())//
						.productBakweight(productVo.getProductLeftweight())//
						.productIrontype(productVo.getProductIrontype())//
						.productIrontypeweight(productVo.getProductIrontypeweight())//
						.productMaterialname(productVo.getProductMaterialname())//
						.productImgid(productVo.getProductImgid())//
						.productMaterialsource(productVo.getProductMaterialsource())//
						.productStatus(productVo.getProductStatus())//
						.productRemark(productVo.getProductRemark()).build();
				pd.setProductOperateIp("127.0.0.1");
				pd.setProductOperateTime(new Date());
				pd.setProductOperator("user01");
				MesProductMapper mapper = sqlSession.getMapper(MesProductMapper.class);
				//批量增加
				mapper.insertSelective(pd);
			}
		}
	}
	
	
	
	
	




}
