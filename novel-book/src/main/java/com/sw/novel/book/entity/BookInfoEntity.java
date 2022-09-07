package com.sw.novel.book.entity;

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
@TableName("book_info")
@ApiModel(value="BookInfoEntity对象", description="")
public class BookInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说书名")
    private String bookName;

    @ApiModelProperty(value = "小说所属分类id")
    private Long categoryId;

    @ApiModelProperty(value = "小说作者id")
    private Long authorId;

    @ApiModelProperty(value = "小说作者姓名")
    private String authorName;

    @ApiModelProperty(value = "小说封面图片")
    private String image;

    @ApiModelProperty(value = "小说描述")
    private String description;

    @ApiModelProperty(value = "小说总字数")
    private Long wordCount;

    @ApiModelProperty(value = "小说点击量")
    private Long viewCount;

    @ApiModelProperty(value = "最新更新章节id")
    private Long recentChapter;

    @ApiModelProperty(value = "是否完结，0：未完结，1：已完结")
    private Integer isFinished;

    @ApiModelProperty(value = "是否免费，0：免费，1：vip")
    private Integer isFree;

    @ApiModelProperty(value = "状态，0：正常，1：下架")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间 ")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
