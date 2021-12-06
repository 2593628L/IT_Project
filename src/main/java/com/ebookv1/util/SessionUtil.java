package com.ebookv1.util;

import com.ebookv1.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static Model setUser(HttpSession session, Model model){
        User sessionU = (User) session.getAttribute("user");
        if(sessionU==null){
            System.out.println("true");
            model.addAttribute("user",null);
        }else{
            System.out.println(sessionU.getUsername());
            User u = (User) session.getAttribute("user");
            model.addAttribute("user",u);
        }
        return model;
    }
}
