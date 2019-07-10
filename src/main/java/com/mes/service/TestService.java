package com.mes.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mes.dao.TestDemoMapper;
import com.mes.model.TestDemo;

@Service
public class TestService {

	@Resource
	private TestDemoMapper mapper;
	
	
	public void persist(TestDemo testDemo) {
		mapper.insertSelective(testDemo);
	}
	
}
