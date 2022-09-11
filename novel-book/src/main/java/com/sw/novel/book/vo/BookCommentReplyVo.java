package com.sw.novel.book.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunao
 * @since 2022/9/11
 * description: 展示回复的评论
 */
@Data
public class BookCommentReplyVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("回复内容")
    private String replyContent;

    @ApiModelProperty(value = "评论用户的id")
    private Long userId;

    @ApiModelProperty(value = "评论用户的昵称")
    private String UserNickname;

    @ApiModelProperty(value = "评论用户的头像")
    private String userAvatar;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
