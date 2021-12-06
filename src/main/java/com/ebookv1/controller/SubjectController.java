package com.ebookv1.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.Comment;
import com.ebookv1.entity.Course;
import com.ebookv1.entity.Subject;
import com.ebookv1.entity.User;
import com.ebookv1.service.CommentService;
import com.ebookv1.service.CourseService;
import com.ebookv1.service.SubjectService;
import com.ebookv1.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    CourseService courseService;
    @Autowired
    CommentService commentService;
    @GetMapping("/")
    public String findAllSubject(Model model, HttpSession session){
        List<Subject> subjects = subjectService.list(null);
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        model.addAttribute("subjects",subjects);
        return "index";
    }
    @GetMapping("/subject/{id}")
    public String CourseList(@PathVariable Long id, Model model,HttpSession session){
       model = SessionUtil.setUser(session,model);
       Subject subject = subjectService.getById(id);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>();
        wrapper.eq("subject_id",id);
        commentQueryWrapper.eq("subject_id",id);
        List<Comment> commentList = commentService.list(commentQueryWrapper);
        List<Course> courseLists =courseService.list(wrapper);
        int number = courseLists.size();
        model.addAttribute("courseLists",courseLists);
        model.addAttribute("commentLists",commentList);
        model.addAttribute("number",number);
        model.addAttribute("subject",subject);
        return "courseList";
    }
    @PostMapping("/addSubject")
    public String addSubject(Subject subject){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("name",subject.getName());
        Subject s = subjectService.getOne(subjectQueryWrapper);
        if(s==null)
        subjectService.save(subject);
        return "redirect:/";
    }

    @GetMapping("/addSubject")
    public String Subject(Model model,HttpSession session){
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        return "addSubject";
    }
}

