package com.yoyo.controller;

import com.yoyo.entity.Admins;
import com.yoyo.util.SafeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @RequestMapping("/login")
    public String login(Admins admins, HttpServletRequest request) {
        try {
            //因为后台管理系统使用shiro进行权限控制,所以登录需要使用shiro的方法操作
            String encode = SafeUtil.encode(admins.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(admins.getUsername(), encode);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);//shiro的登录操作
            //登陆成功后,跳转到首页
            request.setAttribute("msg", "恭喜你,登陆成功");
            return "/admin/index";
        }catch(UnknownAccountException e) {
            //捕获异常,说明用户不存在
            request.setAttribute("msg","用户不存在");
            e.printStackTrace();
        }catch(IncorrectCredentialsException e) {
            request.setAttribute("msg","密码错误");
            e.printStackTrace();
        }catch(LockedAccountException e) {
            request.setAttribute("msg","账户已被锁定");
            e.printStackTrace();
        }catch(AuthenticationException e) {
            request.setAttribute("msg","登陆失败");
            e.printStackTrace();
        }
        return "/admin/login";
    }
}
