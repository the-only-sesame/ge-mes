package com.mes.param;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MesProductVo {
    private Integer id;

    private Integer pId;
    
    private String productId;
    
    private Integer productOrderid;

    private Integer productPlanid;

    private Float productTargetweight;

    private Float productRealweight;

    private Float productLeftweight;

    private Float productBakweight;

    private String productIrontype;

    private Float productIrontypeweight;
//    @NotBlank(message="材料名称不可为空")
    private String productMaterialname;

    private String productImgid;

    private String productMaterialsource;

    private Integer productStatus;

    private String productRemark;

    private String productOperator;

    private String productOperateTime;

    private String productOperateIp;
//    @Min(1)
    private Integer counts=1;
}