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
 * description: 展示小说评论信息
 */
@Data
public class BookCommentVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论用户的id")
    private Long userId;

    @ApiModelProperty(value = "评论用户的昵称")
    private String UserNickname;

    @ApiModelProperty(value = "评论用户的头像")
    private String userAvatar;

    @ApiModelProperty(value = "回复数量")
    private Long replyCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("回复的评论")
    private List<BookCommentReplyVo> bookCommentReplyVoList;
}
