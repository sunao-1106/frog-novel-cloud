package com.sw.common.to;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Data
public class BookContentTo implements Serializable {

    private Integer id;

    private Long chapterId;

    private String chapterContent;

}
