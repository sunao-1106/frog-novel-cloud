package com.sw.novel.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (BookInfo)表实体类
 *
 * @author makejava
 * @since 2022-09-13 18:52:17
 */
@SuppressWarnings("serial")
public class BookInfo extends Model<BookInfo> {
    //主键
    private Long id;
    //小说书名
    private String bookName;
    //小说所属分类id
    private Long categoryId;
    //小说作者id
    private Long authorId;
    //小说作者姓名
    private String authorName;
    //小说封面图片
    private String image;
    //小说描述
    private String description;
    //小说总字数
    private Long wordCount;
    //小说点击量
    private Long viewCount;
    //最新更新章节id
    private Long recentChapterId;
    //最新更新章节名
    private String recentChapterName;
    //最新更新章节时间
    private Date recentChapterUpdateTime;
    //是否完结，0：未完结，1：已完结
    private Integer isFinished;
    //是否免费，0：免费，1：vip
    private Integer isFree;
    //状态，0：正常，1：下架
    private Integer status;
    //逻辑删除，1：已删除
    private Integer isDeleted;
    //创建时间
    private Date creatTime;
    //更新时间
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getWordCount() {
        return wordCount;
    }

    public void setWordCount(Long wordCount) {
        this.wordCount = wordCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getRecentChapterId() {
        return recentChapterId;
    }

    public void setRecentChapterId(Long recentChapterId) {
        this.recentChapterId = recentChapterId;
    }

    public String getRecentChapterName() {
        return recentChapterName;
    }

    public void setRecentChapterName(String recentChapterName) {
        this.recentChapterName = recentChapterName;
    }

    public Date getRecentChapterUpdateTime() {
        return recentChapterUpdateTime;
    }

    public void setRecentChapterUpdateTime(Date recentChapterUpdateTime) {
        this.recentChapterUpdateTime = recentChapterUpdateTime;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
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

