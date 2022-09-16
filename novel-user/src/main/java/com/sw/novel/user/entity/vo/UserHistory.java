package com.sw.novel.user.entity.vo;

import lombok.Data;

/**
 * @author 万先生
 * @version 1.0
 * Create by 2022/9/14 23:09
 */
@Data
public class UserHistory {

     //user-bookself
    //上一次阅读该小说的章节名字
    private Long chapterName;

    //文章所属分类
    //分类名称
    private String categoryName;
    //小说书名
    private String bookName;
    //最新更新章节时间
    private String recentChapterUpdateTime;

}
