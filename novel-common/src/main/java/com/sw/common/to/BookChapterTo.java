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
public class BookChapterTo implements Serializable {

    private Long id;

    private Long bookId;

    private Integer chapterIndex;

    private String chapterName;

    private Long wordCount;

    private Integer isFree;

    private Integer isDeleted;

    private Date createTime;

    private Date updateTime;

    private BookContentTo bookContentTo;

}
