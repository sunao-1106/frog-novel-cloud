package com.sw.novel.home.entity;

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
 * @since 2022-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_book")
@ApiModel(value="HomeBookEntity对象", description="")
public class HomeBookEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说id")
    private Long bookId;

    @ApiModelProperty(value = "类型，0：轮播图，1：猜你喜欢，2：精选完本，3：本周点击榜，4：最新更新，5：本月点击榜")
    private Integer type;

    @ApiModelProperty(value = "排序，预留字段")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
