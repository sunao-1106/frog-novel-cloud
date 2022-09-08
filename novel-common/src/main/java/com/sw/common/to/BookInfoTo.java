package com.sw.common.to;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Data
public class BookInfoTo implements Serializable {

    private Long id;

    private String bookName;

    private Long categoryId;

    private Long authorId;

    private String authorName;

    private String image;

    private String description;

    private Long wordCount;

    private Long viewCount;

    private Long recentChapter;

    private Integer isFinished;

    private Integer isFree;

    private Integer status;

    private Integer isDeleted;

    private Date creatTime;

    private Date updateTime;

}
