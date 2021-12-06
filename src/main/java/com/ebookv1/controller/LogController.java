package com.ebookv1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebookv1.entity.User;
import com.ebookv1.service.UserService;
import com.ebookv1.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LogController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping("/sign")
    public String signinPage(){
        return "sign";
    }

    @PostMapping("/signup")
    public String signUp(User user){
        String name = user.getUsername();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",name);
        User u = userService.getOne(userQueryWrapper);
        if(u==null){
            String password = user.getPassword();
            password = MD5.codeEncryption(password);
            user.setPassword(password);
            userService.save(user);
            return "login";
        }
        return "sign";
    }

    @PostMapping("/login")
    public String login(User user,HttpSession session){
        String username = user.getUsername();
        String password = user.getPassword();
        password = MD5.codeEncryption(password);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        userQueryWrapper.eq("password",password);
        user.setPassword(null);
        session.setAttribute("user",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/";
    }
}
