package com.ebookv1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.Book;
import com.ebookv1.entity.Course;
import com.ebookv1.entity.Subject;
import com.ebookv1.entity.User;
import com.ebookv1.service.BookService;
import com.ebookv1.service.CourseService;
import com.ebookv1.service.SubjectService;
import com.ebookv1.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    BookService bookService;
    @Autowired
    CourseService courseService;
    @Autowired
    SubjectService subjectService;
    @PostMapping("/search")
    public String search(@RequestParam("searchContent") String s, HttpSession session, Model model){
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        QueryWrapper<Book> bookQuery = new QueryWrapper<>();
        QueryWrapper<Course> courseQuery = new QueryWrapper<>();
        QueryWrapper<Subject> subjectQuery = new QueryWrapper<>();
        bookQuery.like("name",s);
        bookQuery.or();
        bookQuery.like("description",s);
        courseQuery.like("name",s);
        subjectQuery.like("name",s);
        List<Book> bookList = bookService.list(bookQuery);
        List<Course> courseList = courseService.list(courseQuery);
        List<Subject> subjectList = subjectService.list(subjectQuery);
        model.addAttribute("bookLists",bookList);
        model.addAttribute("courseLists",courseList);
        model.addAttribute("subjects",subjectList);
        return "search";
    }
}
