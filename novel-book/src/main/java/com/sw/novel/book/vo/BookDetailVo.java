package com.sw.novel.book.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author sunao
 * @since 2022/9/11
 * description: 展示小说详细信息
 */
@Data
public class BookDetailVo {

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

    @ApiModelProperty(value = "最新更新章节id")
    private Long recentChapterId;

    @ApiModelProperty(value = "最新更新章节名")
    private String recentChapterName;

    @ApiModelProperty(value = "最新更新章节时间")
    private Date recentChapterUpdateTime;

    @ApiModelProperty(value = "是否完结，0：未完结，1：已完结")
    private Integer isFinished;

    @ApiModelProperty(value = "是否免费，0：免费，1：vip")
    private Integer isFree;

    @ApiModelProperty(value = "小说章节信息")
    private List<BookChapterVo> bookChapterVoList;

    @ApiModelProperty("小说评论信息")
    private List<BookCommentVo> bookCommentVoList;
}
