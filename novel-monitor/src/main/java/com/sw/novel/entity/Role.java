package com.sw.novel.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Role)表实体类
 *
 * @author makejava
 * @since 2022-09-07 21:26:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class Role extends Model<Role> implements Serializable{

    private static final long serialVersionUID = -54979041104113736L;

    //主键
    private Long id;
    //角色名称
    private String roleName;
    //角色标识
    private String roleSign;


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

