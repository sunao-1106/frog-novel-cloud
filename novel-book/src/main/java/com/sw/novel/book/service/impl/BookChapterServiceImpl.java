package com.sw.novel.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sw.common.to.BookChapterTo;
import com.sw.novel.book.dao.BookContentMapper;
import com.sw.novel.book.dao.BookInfoMapper;
import com.sw.novel.book.entity.BookChapterEntity;
import com.sw.novel.book.dao.BookChapterMapper;
import com.sw.novel.book.entity.BookContentEntity;
import com.sw.novel.book.entity.BookInfoEntity;
import com.sw.novel.book.exception.IllegalAccessBookException;
import com.sw.novel.book.service.BookChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.book.vo.BookChapterContentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Service
public class BookChapterServiceImpl extends ServiceImpl<BookChapterMapper, BookChapterEntity> implements BookChapterService {

    @Autowired
    private BookContentMapper bookContentMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public void saveChapter(BookChapterTo chapterTo) {
        // TODO 判断当前章节是否在表中已存在
        BookChapterEntity bookChapterEntity = new BookChapterEntity();
        BeanUtils.copyProperties(chapterTo, bookChapterEntity);
        // 封装其它参数
        bookChapterEntity.setCreateTime(new Date());
        bookChapterEntity.setUpdateTime(new Date());
        // 添加到数据库
        this.save(bookChapterEntity);

        BookContentEntity bookContentEntity = new BookContentEntity();
        BeanUtils.copyProperties(chapterTo.getBookContentTo(), bookContentEntity);
        // 封装其它参数
        bookContentEntity.setChapterId(bookChapterEntity.getId());
        // 添加到数据库
        bookContentMapper.insert(bookContentEntity);

        // 将该章节更新为book_info表中的最新章节字段
        BookInfoEntity bookInfoEntity = new BookInfoEntity();
        bookInfoEntity.setId(bookChapterEntity.getBookId());
        bookInfoEntity.setRecentChapterId(bookChapterEntity.getId());
        bookInfoEntity.setRecentChapterName(bookChapterEntity.getChapterName());
        bookInfoEntity.setRecentChapterUpdateTime(new Date());
        bookInfoMapper.updateById(bookInfoEntity);
    }

    @Override
    public BookChapterContentVo getFreeChapterContent(Long id) {
        BookChapterContentVo vo = new BookChapterContentVo();
        // 查询出该章节的信息
        BookChapterEntity bookChapterEntity = this.getById(id);
        if (bookChapterEntity != null) {
            if (bookChapterEntity.getIsFree() != 0) {
                // 该章节需要vip权限（防止恶意通过该接口访问vip小说内容）
                throw new IllegalAccessBookException();
            }

            // 封装参数
            vo.setChapterName(bookChapterEntity.getChapterName());
            vo.setChapterId(bookChapterEntity.getId());
            vo.setCreateTime(bookChapterEntity.getCreateTime());

            // TODO 查询该小说对应的分类名称

            // 查询该小说的作者姓名
            BookInfoEntity bookInfoEntity = bookInfoMapper.selectOne(
                    new LambdaQueryWrapper<BookInfoEntity>()
                            .eq(BookInfoEntity::getId, bookChapterEntity.getBookId())
            );
            vo.setAuthorName(bookInfoEntity.getAuthorName());
        }
        return vo;
    }
}
