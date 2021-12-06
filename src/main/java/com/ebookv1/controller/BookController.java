package com.ebookv1.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.Book;
import com.ebookv1.entity.BookCourse;
import com.ebookv1.entity.Course;
import com.ebookv1.entity.User;
import com.ebookv1.service.BookCourseService;
import com.ebookv1.service.BookService;
import com.ebookv1.service.CourseService;
import com.ebookv1.util.FileUtil;
import com.ebookv1.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    CourseService courseService;
    @Autowired
    BookCourseService bookCourseService;

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model, HttpSession session) {
        Book book = bookService.getById(id);
        double s=0.0;
        if(book.getLikes()==null) book.setLikes(0);
        if(book.getCredits()!=null&&book.getViews()!=null &&book.getViews()!=0) s=help(book.getCredits(),book.getViews());
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        model.addAttribute("book", book);
        model.addAttribute("score", s);
        return "book";
    }
    @Transactional
    @PostMapping("/likebook")
    public String likebook(@RequestParam("bookBookid") Long bookid,Model model){
        Book book = bookService.getById(bookid);
        int like = book.getLikes()==null?0:book.getLikes();
        like++;
        book.setLikes(like);
        bookService.updateById(book);
        model.addAttribute("book", book);
        return "book :: likePo";
    }
    @PostMapping("/bookScore")
    public String scorebook(@RequestParam("bookBookid") Long bookid, @RequestParam("score") int points, Model model){
        Book book = bookService.getById(bookid);
        int score = book.getCredits()==null?0:book.getCredits();
        int views = book.getViews()==null?0:book.getViews();
        views++;
        score+=points;
        book.setCredits(score);
        book.setViews(views);
        bookService.updateById(book);
        double s = help(score,views);
        model.addAttribute("score", s);
        return "book :: Score";
    }
private double help(int score,int view){
        if(score>view&&score<view*1.5) return 1.0;
        else if(score>=1.5*view&&score<2*view) return 1.5;
        else if(score>=2*view&&score<2.5*view) return 2.0;
        else if(score>=2.5*view&&score<3*view) return 2.5;
        else if(score>=3*view&&score<3.5*view) return 3.0;
        else if(score>=3.5*view&&score<4*view) return 3.5;
        else if(score>=4*view&&score<4.5*view) return 4.0;
        else return 4.0;
}




    @GetMapping("/input")
    public String bookInput(Model model) {
        List<Course> courses = courseService.list(null);
        model.addAttribute("courses", courses);
        return "addBook";
    }
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("bookname") String bookname, @RequestParam("author")String author,
                         @RequestParam("description")String description,@RequestParam("courseId")String courseId) throws IOException {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("name",bookname);
        Book book = bookService.getOne(bookQueryWrapper);
        if(book!=null) return "test";
            book = new Book();
            book.setAuthor(author);
            book.setName(bookname);
            book.setDescription(description);
            bookService.save(book);
            book = bookService.getOne(bookQueryWrapper);
            Long bookid = book.getBookid();
            BookCourse bookCourse = new BookCourse();
            bookCourse.setBookBookid(bookid);
            Long courseid = Long.parseLong(courseId);
            bookCourse.setCourseId(courseid);
            bookCourseService.save(bookCourse);
            String filePosition=FileUtil.uploadFile(file,bookid);
            book.setPosition(filePosition);
            bookService.updateById(book);
            return "redirect:/course/"+courseId;
    }
}



