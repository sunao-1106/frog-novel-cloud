package com.sw.novel.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sw.common.to.BookInfoTo;
import com.sw.novel.book.dao.BookChapterMapper;
import com.sw.novel.book.dao.BookCommentMapper;
import com.sw.novel.book.dao.BookCommentReplyMapper;
import com.sw.novel.book.entity.BookChapterEntity;
import com.sw.novel.book.entity.BookInfoEntity;
import com.sw.novel.book.dao.BookInfoMapper;
import com.sw.novel.book.service.BookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.novel.book.vo.BookChapterVo;
import com.sw.novel.book.vo.BookDetailVo;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunao
 * @since 2022-09-07
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfoEntity> implements BookInfoService {

    @Autowired
    private BookChapterMapper bookChapterMapper;

    @Autowired
    private BookCommentMapper bookCommentMapper;

    @Autowired
    private BookCommentReplyMapper bookCommentReplyMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Long saveBookInfo(BookInfoTo bookInfoTo) {
        // TODO 判断当前小说是否在表中已存在
        BookInfoEntity bookInfoEntity = new BookInfoEntity();
        BeanUtils.copyProperties(bookInfoTo, bookInfoEntity);
        // 封装其它参数信息
        bookInfoEntity.setCreatTime(new Date());
        bookInfoEntity.setUpdateTime(new Date());
        this.save(bookInfoEntity);
        return bookInfoEntity.getId();
    }

    @Override
    public BookInfoTo getBookById(Long id) {
        BookInfoTo bookInfoTo = new BookInfoTo();
        BeanUtils.copyProperties(this.getById(id), bookInfoTo);
        return bookInfoTo;
    }

    @Override
    public void addViewCountByBookId(Long bookId, Integer viewCountOnceAdd) {
        this.baseMapper.addViewCountByBookId(bookId, viewCountOnceAdd);
    }

    @Override
    public List<BookInfoTo> getRecentUpdateBookList() {
        List<BookInfoEntity> recentUpdateBookList = this.baseMapper.getRecentUpdateBookList(16);
        List<BookInfoTo> bookInfoToList = recentUpdateBookList.stream().map(item -> {
            BookInfoTo bookInfoTo = new BookInfoTo();
            BeanUtils.copyProperties(item, bookInfoTo);
            return bookInfoTo;
        }).collect(Collectors.toList());
        return bookInfoToList;
    }

    @Override
    public BookDetailVo getBookDetailById(Long id) {
        // 增加该小说的浏览量
        this.addViewCount(id);

        BookDetailVo bookDetailVo = new BookDetailVo();

        // 查询当前小说的详细信息: book_info
        BookInfoEntity bookInfoEntity = this.getById(id);
        BeanUtils.copyProperties(bookInfoEntity, bookDetailVo);

        // 查询当前小说下的章节目录
        List<BookChapterEntity> bookChapterEntityList = bookChapterMapper.selectList(
                new LambdaQueryWrapper<BookChapterEntity>()
                        .eq(BookChapterEntity::getBookId, id)
        );
        // 转化为Vo
        List<BookChapterVo> bookChapterVoList = bookChapterEntityList.stream().map(item -> {
            BookChapterVo bookChapterVo = new BookChapterVo();
            BeanUtils.copyProperties(item, bookChapterVo);
            return bookChapterVo;
        }).collect(Collectors.toList());
        bookDetailVo.setBookChapterVoList(bookChapterVoList);

        // TODO 查询当前小说下所有的评论信息
        return bookDetailVo;
    }

    /**
     * 增加该小说的浏览量
     */
    private void addViewCount(Long bookId) {
        rabbitTemplate.convertAndSend("book.add.view.exchange", "book.clicked", bookId);
    }

}
