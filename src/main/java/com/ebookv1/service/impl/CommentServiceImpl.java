package com.ebookv1.service.impl;

import com.ebookv1.entity.Comment;
import com.ebookv1.mapper.CommentMapper;
import com.ebookv1.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
