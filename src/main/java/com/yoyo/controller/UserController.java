package com.yoyo.controller;

import com.mysql.cj.Session;
import com.yoyo.entity.Users;
import com.yoyo.service.ITypeService;
import com.yoyo.service.IUserService;
import com.yoyo.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Pattern;

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
        //判断页面输入的用户名、密码和手机号是否为空,如果为空,不能注册
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg", "用户名不能为空");
            return "/index/register";
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg", "密码不能为空");
            return "/index/register";
        }
        if (users.getPhone() == null || users.getPhone().trim().isEmpty() || !Pattern.matches("^1[3-9]\\d{9}$",users.getPhone())) {
            //返回给页面错误提示信息
            request.setAttribute("msg", "手机号不合法");
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
        session.removeAttribute("order");
        //跳转到登录页面
        return "redirect:login?flag=-1";
    }

    @RequestMapping("/toforget")
    public String toForgrt(HttpServletRequest request) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());
        //跳转到重置密码界面
        return "/index/forget";
    }

    @RequestMapping("/forget")
    public String forget(HttpServletRequest request, Users users) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());

        //判断页面输入的用户名和手机号是否为空,如果为空,不能重置
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            //返回给页面错误提示信息
            request.setAttribute("msg", "用户名不能为空");
            return "/index/forget";
        }
        if (users.getPhone() == null || users.getPhone().trim().isEmpty() || !Pattern.matches("^1[3-9]\\d{9}$",users.getPhone())) {
            //返回给页面错误提示信息
            request.setAttribute("msg", "手机号不合法");
            return "/index/forget";
        }

        //重置密码
        Users user = iUserService.check(users.getUsername().trim(), users.getPhone().trim());
        if (user != null) {
            //查询到用户信息,重置密码为123456
            //更新数据库中对应用户密码
            iUserService.updatePasswordByUserNameAndPhone("123456", user.getUsername().trim(), user.getPhone().trim());
            request.setAttribute("msg", "密码已重置为123456");
            //重置密码成功后,跳转到登录界面
            request.setAttribute("flag", 6);
            return "/index/login";
        }else {
            //未查询到用户信息,返回错误提示信息给页面
            request.setAttribute("msg", "用户名不存在或手机号错误");
            //跳转回重置界面
            return "/index/forget";
        }
    }

    @RequestMapping("/my")
    public String my(Users users, HttpServletRequest request, HttpSession session) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());
        request.setAttribute("flag", 4);

        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null) {
            //没有登录,跳转到登陆界面
            request.setAttribute("msg", "请先登录");
            return "forward:login?flag=-1"; //表示路径 /login?flag=-1
        }

        //因点击header.jsp的个人中心按钮,访问my路径,在my.jsp中点击提交按钮,访问的也是my路径
        //问题:如何区分?
        //判断是否携带了user数据,如果携带了数据,说明是个人中心页面提交修改数据操作,否则,说明是跳转个人中心页面操作
        if (Objects.isNull(users) || Objects.isNull(users.getId())) {
            return "/index/my";
        }
        //获取登录用户的所有信息
        Users user = iUserService.get(users.getId());
        //判断用户id是否存在
        if (user != null) {
            //修改对应用户的收货人姓名,电话和地址
            iUserService.updateUserByid(user.getId(), users.getUsername(), users.getPhone(), users.getAddress());
            request.setAttribute("msg", "收货信息修改成功");
            //同步用户信息
            user.setName(users.getName());
            user.setPhone(users.getPhone());
            user.setAddress(users.getAddress());
            session.setAttribute("user", user);

            //修改用户密码
            //修改成功后,需返回登录界面重新登录
            //页面输入的原密码和数据库中密码一致时,才能修改密码
            if (users.getPassword() != null && !users.getPassword().trim().isEmpty()
                && SafeUtil.encode(users.getPassword()).equals(user.getPassword())) {
                if (users.getPasswordNew() != null && !users.getPasswordNew().trim().isEmpty()){
                    //修改新密码
                    iUserService.updatePassWordById(user.getId(), users.getPasswordNew().trim());
                    request.setAttribute("msg", "密码修改成功");
                    //消除登录状态
                    session.setAttribute("user", null);
                    //跳转到登录页面
                    return "redirect:login?flag=-1"; //表示路径 /login?flag=-1
                }else {
                    request.setAttribute("msg", "新密码不可为空");
                    return "/index/my";
                }
            }else if (!users.getPassword().trim().isEmpty()
                && !SafeUtil.encode(users.getPassword()).equals(user.getPassword())) {
                request.setAttribute("msg", "原密码输入错误");
                return "/index/my";
            }
            return "/index/my";
        }else {
            request.setAttribute("msg", "系统繁忙,请稍后再试");
            return "/index/my"; //表示路径 /index/my.jsp
        }
    }
}
