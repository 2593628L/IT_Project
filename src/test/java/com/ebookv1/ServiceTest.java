package com.ebookv1;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.Book;
import com.ebookv1.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest
public class ServiceTest {
    @Autowired
    BookService bookService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    BookCourseService bookCourseService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Test
    public void testBookService(){
        //get all book
        List<Book> bookList = bookService.list(null);
        for(Book book:bookList){
            System.out.println(book);
        }
        //get a book with particular author
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author","lisi");
        Book book1 = bookService.getOne(queryWrapper);
        Assertions.assertTrue (1464211626481438721L == book1.getBookid());

        //test insert a book into database
        Book book2 = new Book();
        book2.setName("testInserting");
        bookService.save(book2);
    }
    @Test
    public void testSubjectService(){
    }
    @Test
    public void testCourseService(){
    }
    @Test
    public void testCommentService(){
    }
    @Test
    public void testBookCourseService(){
    }
    @Test
    public void testUserService(){
    }
}
