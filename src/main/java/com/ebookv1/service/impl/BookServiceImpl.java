package com.ebookv1.service.impl;

import com.ebookv1.entity.Book;
import com.ebookv1.mapper.BookMapper;
import com.ebookv1.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 17
 * @since 2021-11-16
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
