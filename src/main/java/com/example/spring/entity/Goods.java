package com.example.spring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author wms
 * @since 2023-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "图片")
    private String image;
    @ApiModelProperty(value = "账号")
    private String no;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "商品类别")
    private Integer commodityCategory;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "仓库")
    private Integer storage;

    @ApiModelProperty(value = "销售单价")
    private String salesUnitPrice;

    @ApiModelProperty(value = "生产日期")
    private Integer dateOfManufacture;

    @ApiModelProperty(value = "保质期")
    private Integer qualityGuaranteePeriod;

    @ApiModelProperty(value = "是否有效，Y有效，其他无效")
    @TableField("isValid")
    private String isvalid;


}
