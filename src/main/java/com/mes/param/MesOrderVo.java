package com.mes.param;

import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MesOrderVo{
	//�����������ɵ�order����
	@Min(1)
	private Integer count=1;//������־�����û��ֵ��Ĭ����1
	
    private Integer id;

    private String orderId;

    private String orderClientname;

    private String orderProductname;

    private String orderContractid;

    private String orderImgid;

    private String orderMaterialname;

    private Date orderCometime;

    private Date orderCommittime;

    private Integer orderInventorystatus;

    private String orderSalestatus;

    private String orderMaterialsource;

    private Integer orderHurrystatus;

    private Integer orderStatus;

    private String orderRemark;
    
    @NotBlank(message="�������ڲ�����Ϊ��")
    private String comeTime;
    
    @NotBlank(message="�ύ���ڲ�����Ϊ��")
    private String commitTime;
    
}
