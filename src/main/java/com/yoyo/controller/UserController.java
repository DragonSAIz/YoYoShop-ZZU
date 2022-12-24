package com.yoyo.controller;

import com.yoyo.entity.Users;
import com.yoyo.service.ITypeService;
import com.yoyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ITypeService iTypeService;

    @RequestMapping("/register")
    public String register(@RequestParam(required = false, defaultValue = "0") int flag, Users users, HttpServletRequest request) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());
        //需要根据flag的值判断是跳转到注册页面,还是进行注册操作
        //如果flag是-1,说明头部导航注册按钮被点击,跳转到注册页面
        if (flag == -1) {
            //返回页面flag=5,表示头部导航注册按钮被选中
            request.setAttribute("flag", 5);
            return "/index/register"; //表示: /index/register.jsp
        }
        //如果flag不是-1,说明是注册页面的提交按钮被点击,进行注册用户操作
        //判断页面输入的用户名和密码是否为空,如果为空,不能注册
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg","用户名不能为空");
            return "/index/register";
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg","密码不能为空");
            return "/index/register";
        }
        //判断要注册的用户是否在数据库中已经有同名用户
        if (iUserService.isExist(users.getUsername().trim())) {
            //返回给页面有同名,不能注册
            request.setAttribute("msg", "用户名已存在");
            return "/index/register";
        }
        //没有同名用户,则将用户数据传入数据库
        users.setUsername(users.getUsername().trim());
        users.setPassword(users.getPassword().trim());
        iUserService.add(users);

        return "redirect:login?flag=-1";//注册成功后,自动跳转到登录页面
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false, defaultValue = "0") int flag, Users users, HttpServletRequest request, HttpSession session) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());
        //需要根据flag的值判断是跳转到登录页面,还是进行登录操作
        //如果flag是-1,跳转到登录页面
        if (flag == -1) {
            //返回页面flag=6,表示头部导航登录按钮被选中
            request.setAttribute("flag", 6);
            return "/index/login"; //表示: /index/login.jsp
        }
        //如果flag不是-1,说明是登录页面的提交按钮被点击,进行登录用户操作
        //判断页面输入的用户名和密码是否为空,如果为空,不能登录
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg","用户名不能为空");
            return "/index/login";
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg","密码不能为空");
            return "/index/login";
        }

        Users user = iUserService.checkUser(users.getUsername().trim(), users.getPassword().trim());
        if (user != null) {
            //查询用户信息,保存登陆状态,表示登录成功
            //保存登录状态
            session.setAttribute("user", user);
            //跳转到主页
            return "redirect:index";
        }else {
            //未查询到用户信息,返回错误提示信息给页面
            request.setAttribute("msg", "用户名或密码错误");
            //跳转回登录界面
            return "/index/login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //消除登录状态
        session.setAttribute("user", null);
        //跳转到登录页面
        return "redirect:login?flag=-1";
    }
}
