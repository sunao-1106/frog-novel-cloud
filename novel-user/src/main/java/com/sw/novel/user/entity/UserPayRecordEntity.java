package com.sw.novel.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author sunao
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_pay_record")
@ApiModel(value="UserPayRecordEntity对象", description="")
public class UserPayRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "购买数量，以天为单位，1个月=30天")
    private Integer payAmount;

    @ApiModelProperty(value = "购买金额")
    private Integer payCount;

    @ApiModelProperty(value = "逻辑删除：0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
