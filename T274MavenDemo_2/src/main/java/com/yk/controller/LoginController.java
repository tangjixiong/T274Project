package com.yk.controller;

import com.yk.entity.User;
import com.yk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    //进入登录界面
    @RequestMapping("/login.html")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public  String login(String userCode, String userPassword, Model model, HttpSession session) throws Exception{
        User user=userService.login(userCode,userPassword);
        if(user!=null){//登录成功
            //保存到session
            session.setAttribute("userSession",user);
            return "redirect:/sys/main.html";
        }else{
            //登录失败
            model.addAttribute("error","用户名或密码错误");
            /*return "login";*/
            throw new RuntimeException("用户名或密码错误");
        }
    }

    //注销
    @RequestMapping("/logout.html")
    public String loginOut( HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }
}
