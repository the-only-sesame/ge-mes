package com.mes.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.mes.dao.MesOrderCustomerMapper;
import com.mes.dao.MesOrderMapper;
import com.mes.exception.SysMineException;
import com.mes.model.MesOrder;
import com.mes.param.MesOrderVo;
import com.mes.util.BeanValidator;
import com.mes.util.MyStringUtils;


@Service
public class OrderService {
	//一开始就定义一个id生成器
	private IdGenerator ig = new IdGenerator();
	@Resource
	private MesOrderMapper mesOrderMapper;
	@Resource
	private SqlSession sqlSession;
	@Resource
	private PlanService planService;
	@Resource
	private MesOrderCustomerMapper mesOrderCustomerMapper;

	public void orderBatchInserts(MesOrderVo mesOrderVo) {
		//数据校验
		BeanValidator.check(mesOrderVo);
		//先去判断是否是批量添加
		Integer counts = mesOrderVo.getCount();
		//根据counts的个数，生成需要添加的ids的数据集合
		//zx190001 zx190003
		List<String> ids = createOrderIdsDefault(Long.valueOf(counts));
		//sql的批量添加处理
		MesOrderMapper mesOrderBatchMapper = sqlSession.getMapper(MesOrderMapper.class);
		for (String orderid : ids) {
			try {
				// 将vo转换为po
				MesOrder mesOrder = MesOrder.builder().orderId(orderid)
						.orderClientname(mesOrderVo.getOrderClientname())//
						.orderProductname(mesOrderVo.getOrderProductname()).orderContractid(mesOrderVo.getOrderContractid())//
						.orderImgid(mesOrderVo.getOrderImgid()).orderMaterialname(mesOrderVo.getOrderMaterialname())
						.orderCometime(MyStringUtils.string2Date(mesOrderVo.getComeTime(), null))//
						.orderCommittime(MyStringUtils.string2Date(mesOrderVo.getCommitTime(), null))
						.orderInventorystatus(mesOrderVo.getOrderInventorystatus()).orderStatus(mesOrderVo.getOrderStatus())//
						.orderMaterialsource(mesOrderVo.getOrderMaterialsource())
						.orderHurrystatus(mesOrderVo.getOrderHurrystatus()).orderStatus(mesOrderVo.getOrderStatus())
						.orderRemark(mesOrderVo.getOrderRemark()).build();

				// 设置用户的登录信息
				// TODO
				mesOrder.setOrderOperator("tom");
				mesOrder.setOrderOperateIp("127.0.0.1");
				mesOrder.setOrderOperateTime(new Date());
				// 批量添加未启动订单
				if (mesOrder.getOrderStatus() == 1) {
					planService.prePlan(mesOrder);
				}
				mesOrderBatchMapper.insertSelective(mesOrder);
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}
		}
	}

	//获取id集合
	public List<String> createOrderIdsDefault(Long ocounts){
		if(ig == null) {
			ig = new IdGenerator();
		}
		ig.setCurrentdbidscount(getOrderCount());
		List<String> list = ig.initIds(ocounts);
		ig.clear();
		return list;
	}
	// 获取数据库所有的数量
		public Long getOrderCount() {
			return mesOrderCustomerMapper.getOrderCount();
		}
	
///////////////////////////////////////////////////////////////////////////////////////////////
// 1 默认生成代码
// 2 手工生成代码
// id生成器
class IdGenerator {
// 数量起始位置
private Long currentdbidscount;
private List<String> ids = new ArrayList<String>();
private String idpre;
private String yearstr;
private String idafter;

public IdGenerator() {
}

public Long getCurrentdbidscount() {
return currentdbidscount;
}

public void setCurrentdbidscount(Long currentdbidscount) {
this.currentdbidscount = currentdbidscount;
if (null == this.ids) {
this.ids = new ArrayList<String>();
}
}

public List<String> getIds() {
return ids;
}

public void setIds(List<String> ids) {
this.ids = ids;
}

public String getIdpre() {
return idpre;
}

public void setIdpre(String idpre) {
this.idpre = idpre;
}

public String getYearstr() {
return yearstr;
}

public void setYearstr(String yearstr) {
this.yearstr = yearstr;
}

public String getIdafter() {
return idafter;
}

public void setIdafter(String idafter) {
this.idafter = idafter;
}

public List<String> initIds(Long ocounts) {
for (int i = 0; i < ocounts; i++) {
this.ids.add(getIdPre() + yearStr() + getIdAfter(i));
}
return this.ids;
}

//
private String getIdAfter(int addcount) {
// 系统默认生成5位 ZX1700001
int goallength = 5;
// 获取数据库order的总数量+1+循环次数(addcount)
int count = this.currentdbidscount.intValue() + 1 + addcount;
StringBuilder sBuilder = new StringBuilder("");
// 计算与5位数的差值
int length = goallength - new String(count + "").length();
for (int i = 0; i < length; i++) {
sBuilder.append("0");
}
sBuilder.append(count + "");
return sBuilder.toString();
}

private String getIdPre() {
// idpre==null?this.idpre="ZX":this.idpre=idpre;
this.idpre = "ZX";
return this.idpre;
}

private String yearStr() {
Date currentdate = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String yearstr = sdf.format(currentdate).substring(2, 4);
return yearstr;
}

public void clear() {
this.ids = null;
}

@Override
public String toString() {
return "IdGenerator [ids=" + ids + "]";
}
}	
	
}










