package com.sw.novel.book.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunao
 * @since 2022/9/15
 * description: 展示章节内容
 */
@Data
public class BookChapterContentVo {

    @ApiModelProperty(value = "章节id")
    private Long chapterId;

    @ApiModelProperty("作者")
    private String authorName;

    @ApiModelProperty("分类")
    private String categoryName;

    @ApiModelProperty(value = "章节名称")
    private String chapterName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mmmm:ss")
    private Date createTime;

    @ApiModelProperty(value = "章节内容")
    private String chapterContent;

}
