package com.ebookv1.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.*;
import com.ebookv1.service.BookCourseService;
import com.ebookv1.service.BookService;
import com.ebookv1.service.CourseService;
import com.ebookv1.service.SubjectService;
import com.ebookv1.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    BookCourseService bookCourseService;
    @Autowired
    BookService bookService;
    @Autowired
    CourseService courseService;
    @Autowired
    SubjectService subjectService;
    @GetMapping("/{id}")
    public String CourseList(@PathVariable Long id, Model model, HttpSession session){
        QueryWrapper<BookCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<BookCourse> bookCourses = bookCourseService.list(wrapper);
        List<Book> bookList = new ArrayList<>();
        for(BookCourse bookCourse:bookCourses){
            Long bookID = bookCourse.getBookBookid();
            Book book = bookService.getById(bookID);
            bookList.add(book);
        }
        Course course = courseService.getById(id);
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        model.addAttribute("bookLists",bookList);
        model.addAttribute("number",bookList.size());
        model.addAttribute("course",course);
        return "course";
    }


    @GetMapping("/addCourse")
    public String addCourse(Model model,HttpSession session){
        List<Subject> subjects = subjectService.list(null);
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        model.addAttribute("subjects",subjects);
        model.addAttribute("course",new Course());
        return "addCourse";
    }
    @PostMapping("/addTheCourse")
    public String addTheCourse(Course course){
        Long subjectID = course.getSubjectId();
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("name",course.getName());
        Course c = courseService.getOne(courseQueryWrapper);
        if(c==null)
            courseService.save(course);
        String path="redirect:/subject/";
        path+=subjectID;
        return path;
    }
}

