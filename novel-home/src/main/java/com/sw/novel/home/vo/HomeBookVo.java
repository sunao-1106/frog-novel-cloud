package com.sw.novel.home.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunao
 * @since 2022/9/8
 * description:
 */
@Data
public class HomeBookVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "小说书名")
    private String bookName;

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

    @ApiModelProperty(value = "创建时间 ")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
