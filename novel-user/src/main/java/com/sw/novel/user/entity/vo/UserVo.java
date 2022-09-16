package com.sw.novel.user.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/13 17:35
 * 用户信息返回类型
 */
@Data
public class UserVo {

    //用户名
    //private String username;
    //昵称
    private String nickname;
    //头像
    private String avatar;

    //user-pay-record
    //会员 为0非会员为 1为会员
    private int vip;
    //会员时间，以天为单位，1个月=30天
    private String payAmount;



}
