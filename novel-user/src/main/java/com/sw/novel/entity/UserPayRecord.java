package com.sw.novel.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (UserPayRecord)表实体类
 *
 * @author makejava
 * @since 2022-09-13 18:51:20
 */
@SuppressWarnings("serial")
public class UserPayRecord extends Model<UserPayRecord> {
    //主键
    private Long id;
    //用户id
    private Long userId;
    //购买数量，以天为单位，1个月=30天
    private Integer payAmount;
    //购买金额
    private Integer payCount;
    //逻辑删除：0：未删除，1：已删除
    private Integer isDeleted;
    //创建时间
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

