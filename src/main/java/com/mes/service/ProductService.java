package com.mes.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.mes.beans.PageQuery;
import com.mes.beans.PageResult;
import com.mes.dao.MesOrderMapper;
import com.mes.dao.MesProductCustomerMapper;
import com.mes.dao.MesProductMapper;
import com.mes.dto.ProductDto;
import com.mes.dto.SearchProductDto;
import com.mes.exception.SysMineException;
import com.mes.model.MesOrder;
import com.mes.model.MesProduct;
import com.mes.model.MesStock;
import com.mes.param.MesProductVo;
import com.mes.param.SearchProductParam;
import com.mes.util.BeanValidator;

@Service
public class ProductService {

	@Resource
	private SqlSession sqlSession;
	@Resource
	private MesOrderMapper mesOrderMapper;
	@Resource
	private MesProductMapper mesProductMapper;
	
	//钢锭解绑的逻辑
	public boolean unbound(String childId) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	//钢材绑定钢锭的逻辑
	public void bind(String parentId, String childId) {
			Integer pid=Integer.parseInt(parentId);
			Integer cid=Integer.parseInt(childId);
			
			MesProduct parent=mesProductMapper.selectByPrimaryKey(pid);
			MesProduct child=mesProductMapper.selectByPrimaryKey(cid);
			
			//钢材的理论剩余和真实剩余比钢锭的工艺重量大或等
			//后台的健壮性判断，防止页面js效果失效
			if(parent.getProductLeftweight()>=parent.getProductBakweight()&&parent.getProductBakweight()>=child.getProductTargetweight()) {
				//计算钢材的剩余理论重量剩余值
				parent.setProductBakweight(parent.getProductBakweight()-child.getProductTargetweight());
				//计算钢锭的理论重量剩余值，修改status为真，绑定pid
				child.setpId(pid);
				child.setProductBakweight(child.getProductTargetweight());
				child.setProductStatus(1);
				//更新parent与child  钢材 与 钢锭
				mesProductMapper.updateByPrimaryKeySelective(parent);
				mesProductMapper.updateByPrimaryKeySelective(child);
			}
		}
	
	
	//钢材绑定的钢锭列表
		public PageResult<ProductDto> searchPageParentBindList(SearchProductParam param, PageQuery page) {
			// 校验
			BeanValidator.check(page);
			// vo-dto
			SearchProductDto dto = new SearchProductDto();
			if (StringUtils.isNotBlank(param.getSearch_source())) {
				dto.setSearch_source(param.getSearch_source());
			}
			if(param.getPid()!=null) {
				dto.setPid(param.getPid());
			}
			int count = mesProductCustomerMapper.countBySearchParentBindListDto(dto);

			if (count > 0) {
				List<ProductDto> productList = mesProductCustomerMapper.getPageListBySearchParentBindListDto(dto, page);
				return PageResult.<ProductDto>builder().total(count).data(productList).build();
			}

			return PageResult.<ProductDto>builder().build();
		}
	
	// 钢材绑定子材料分页显示
	public PageResult<ProductDto> searchPageChildBindList(SearchProductParam param, PageQuery page) {
			// 校验
		BeanValidator.check(page);
			// vo-dto
		SearchProductDto dto = new SearchProductDto();
		if (StringUtils.isNotBlank(param.getSearch_source())) {
			dto.setSearch_source(param.getSearch_source());
		}
		int count = mesProductCustomerMapper.countBySearchChildBindListDto(dto);

		if (count > 0) {
			List<ProductDto> productList = mesProductCustomerMapper.getPageListBySearchChildBindListDto(dto, page);
			return PageResult.<ProductDto>builder().total(count).data(productList).build();
		}

		return PageResult.<ProductDto>builder().build();
	}
	
	
	
	
	
	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;
	// 一开始就定义一个id生成器
		private IdGenerator ig = new IdGenerator();
	
		public MesProduct selectById(String id) {
			if (StringUtils.isNotBlank(id) && StringUtils.isNotEmpty(id)) {
				MesProduct product = mesProductMapper.selectByPrimaryKey(Integer.parseInt(id));
				if (null != product) {
					return product;
				}
			}
			throw new RuntimeException("没有这个材料");
		}	
		
		public PageResult<ProductDto> searchPageBindList(SearchProductParam param, PageQuery page) {
			// 校验
			BeanValidator.check(page);
			// vo-dto
			SearchProductDto dto = new SearchProductDto();

			if (StringUtils.isNotBlank(param.getKeyword())) {
				dto.setKeyword("%" + param.getKeyword() + "%");
			}
			
			if (StringUtils.isNotBlank(param.getSearch_source())) {
				dto.setSearch_source(param.getSearch_source());
			}
			
			if (param.getSearch_status() != null) {
				dto.setSearch_status(param.getSearch_status());
			}

			int count = mesProductCustomerMapper.countBySearchBindListDto(dto);

			if (count > 0) {
				List<ProductDto> productList = mesProductCustomerMapper.getPageListBySearchBindListDto(dto, page);
				return PageResult.<ProductDto>builder().total(count).data(productList).build();
			}

			return PageResult.<ProductDto>builder().build();
		}

		
		// 批量启动
		public void batchStart(String ids) {
			if (StringUtils.isNotEmpty(ids)) {
				MesProductMapper mapper = sqlSession.getMapper(MesProductMapper.class);
				String[] idStrs = ids.split("&");
				for (String id : idStrs) {
					MesProduct mesProduct = mapper.selectByPrimaryKey(Integer.parseInt(id));
					mesProduct.setProductStatus(1);
					mesProduct.setProductOperateTime(new Date());
					mapper.updateByPrimaryKeySelective(mesProduct);
					
				}
			}
		}
		
		
		
		
		
	//增加材料模块
	public void insert(MesProductVo productVo) {
		//校验
		BeanValidator.check(productVo);
		//首先需要获取到需要增加的个数
		Integer counts=productVo.getCounts();
		
		List<String> ids = createOrderIdsDefault(Long.valueOf(counts));
		
		MesProductMapper mesProductBatchMapper=sqlSession.getMapper(MesProductMapper.class);
		
		if (counts != null && counts > 0) {
			for (String productid : ids) {
				try {
					//builder不会出现线程安全问题
				//批量增加前填充model数据模型                                                                               //随机生成工具UUID
				MesProduct pd = MesProduct.builder().productId(productid)//UUIDUtil.generateUUID
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
						.productConsume(productVo.getProductConsume())//
						.productRemark(productVo.getProductRemark()).build();
				
				StringBuilder sb = new StringBuilder();
		        try {
		            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();//获取本地所有网络接口
		            while (en.hasMoreElements()) {//遍历枚举中的每一个元素
		                NetworkInterface ni= (NetworkInterface) en.nextElement();
		                Enumeration<InetAddress> enumInetAddr = ni.getInetAddresses();
		                while (enumInetAddr.hasMoreElements()) {
		                    InetAddress inetAddress = (InetAddress) enumInetAddr.nextElement();
		                    if (!inetAddress.isLoopbackAddress()  && !inetAddress.isLinkLocalAddress()
		                            && inetAddress.isSiteLocalAddress()) {
		                    	pd.setProductOperateIp(inetAddress.getHostAddress());
		                    	pd.setProductOperator(inetAddress.getHostName());
		                        sb.append("name:" + inetAddress.getHostName().toString()+"\n");
		                        sb.append("ip:" + inetAddress.getHostAddress().toString()+"\n");
		                    }
		                }
		            }
		        } catch (SocketException e) {  }
		        System.out.println(sb.toString());
				pd.setProductOperateTime(new Date());
				
				MesProductMapper mapper = sqlSession.getMapper(MesProductMapper.class);
				//执行批量增加
				mapper.insertSelective(pd);
				} catch (Exception e) {
					throw new SysMineException("创建过程有问题");
				}
			}
		}
	}



	public PageResult<ProductDto> searchPageList(SearchProductParam param, PageQuery page) {
		BeanValidator.check(page);
		SearchProductDto dto = new SearchProductDto();

		if (StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%" + param.getKeyword() + "%");
		}
		if (StringUtils.isNotBlank(param.getSearch_source())) {
			dto.setSearch_source(param.getSearch_source());
			;
		}
		if (param.getSearch_status() != null) {
			dto.setSearch_status(param.getSearch_status());
		}
		
		int count = mesProductCustomerMapper.countBySearchDto(dto);
		
		if (count > 0) {
			List<ProductDto> productList = mesProductCustomerMapper.getPageListBySearchDto(dto, page);
			
			return PageResult.<ProductDto>builder().total(count).data(productList).build();
		}
		
		return PageResult.<ProductDto>builder().build();
	}
	
	
	// 获取id集合
	public List<String> createOrderIdsDefault(Long ocounts) {
		if (ig == null) {
			ig = new IdGenerator();
		}
		ig.setCurrentdbidscount(getProductCount());
		List<String> list = ig.initIds(ocounts);
		ig.clear();
		return list;
	}

	// 获取数据库所有的数量
	public Long getProductCount() {
		return mesProductCustomerMapper.getProductCount();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	class IdGenerator {
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
			// 默认生成 ZX1700001
			int goallength = 5;
			//获取数据库纵向+1次循环(addcount)
			int count = this.currentdbidscount.intValue() + 1 + addcount;
			StringBuilder sBuilder = new StringBuilder("");
			//计算与五位数的差值ֵ
			int length = goallength - new String(count + "").length();
			for (int i = 0; i < length; i++) {
				sBuilder.append("0");
			}
			sBuilder.append(count + "");
			return sBuilder.toString();
		}

		private String getIdPre() {
			// idpre==null?this.idpre="ZX":this.idpre=idpre;
			this.idpre = "mes-YYY-";
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
