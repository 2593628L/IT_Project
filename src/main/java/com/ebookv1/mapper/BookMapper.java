package com.ebookv1.mapper;

import com.ebookv1.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
