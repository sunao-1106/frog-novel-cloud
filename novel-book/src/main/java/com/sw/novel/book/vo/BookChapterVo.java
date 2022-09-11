package com.sw.novel.book.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunao
 * @since 2022/9/11
 * description: 展示小说章节信息
 */
@Data
public class BookChapterVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "章节索引")
    private Integer chapterIndex;

    @ApiModelProperty(value = "章节名称")
    private String chapterName;

    @ApiModelProperty(value = "是否免费，0：免费，1：vip")
    private Integer isFree;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
