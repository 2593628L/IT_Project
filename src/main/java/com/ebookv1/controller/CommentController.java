package com.ebookv1.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.Comment;
import com.ebookv1.entity.User;
import com.ebookv1.service.CommentService;
import com.ebookv1.service.UserService;
import com.ebookv1.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @GetMapping("/comments/{bookid}")
    public String comments(@PathVariable Long bookid, Model model, HttpSession session){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("book_bookid",bookid);
        List<Comment> commentList = commentService.list(commentQueryWrapper);
        User sessionU = (User) session.getAttribute("user");
        model = SessionUtil.setUser(session,model);
        model.addAttribute("comments",commentList);

        return "book :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        comment.setCreateTime(new Date());
        commentService.save(comment);
        Long bookid = comment.getBookBookid();
        Long courseid = comment.getCourseId();
        Long subjectid = comment.getSubjectId();
        if(bookid!=null){
            return "redirect:/comments/"+bookid;
        }else if(courseid!=null){
            return "redirect:/comments/course/"+courseid;
        }else{
            return "redirect:/comments/subject/"+subjectid;
        }

    }
    @GetMapping("/comments/subject/{subjectid}")
    public String subjectComment(@PathVariable Long subjectid,Model model){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("subject_id",subjectid);
        List<Comment> commentList = commentService.list(commentQueryWrapper);
        model.addAttribute("comments",commentList);
        return "courseList :: subjectcomment";
    }

    @GetMapping("/comments/course/{courseid}")
    public String courseComment(@PathVariable Long courseid,Model model){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id",courseid);
        List<Comment> commentList = commentService.list(commentQueryWrapper);
        model.addAttribute("comments",commentList);
        return "course :: coursecomment";
    }
}

