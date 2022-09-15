package com.sw.novel.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (UserBookself)表实体类
 *
 * @author makejava
 * @since 2022-09-13 18:51:58
 */
@SuppressWarnings("serial")
public class UserBookself extends Model<UserBookself> {

    private Long id;
    //用户id
    private Long userId;
    //小说id
    private Long bookId;
    //上一次阅读该小说的章节id
    private Long preContentId;
    //逻辑删除，0：未删除，1：已删除
    private Integer isDeleted;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;


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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPreContentId() {
        return preContentId;
    }

    public void setPreContentId(Long preContentId) {
        this.preContentId = preContentId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

