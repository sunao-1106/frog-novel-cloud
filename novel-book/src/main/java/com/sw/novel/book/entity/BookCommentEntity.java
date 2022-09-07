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
@TableName("book_comment")
@ApiModel(value="BookCommentEntity对象", description="")
public class BookCommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说id")
    private Long bookId;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论用户")
    private Long commentUser;

    @ApiModelProperty(value = "回复数量")
    private Long replyCount;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}