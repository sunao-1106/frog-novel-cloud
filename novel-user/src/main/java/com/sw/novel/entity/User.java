package com.sw.novel.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-09-06 16:09:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")

public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 2095216118;

    //主键
    private Long id;
    //用户角色
    private Long userRole;
    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;
    //性别，0：女，1：男
    private Integer gender;
    //邮箱，预留字段
    private String email;
    //手机号
    private String phone;
    //头像
    private String avatar;
    //状态，0：正常
    private Integer status;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

