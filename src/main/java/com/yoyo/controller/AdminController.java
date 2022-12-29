package com.yoyo.controller;

import com.yoyo.entity.Admins;
import com.yoyo.service.IAdminService;
import com.yoyo.util.PageUtil;
import com.yoyo.util.SafeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

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

    @RequestMapping("logout")
    public String logout(){
        //因为登录使用了shiro，所以退出必须也使用shiro
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/admin/login";
    }

    @RequestMapping("/adminRe")
    public String adminRe(HttpServletRequest request){
        request.setAttribute("flag",5);
        Admins admins = (Admins) SecurityUtils.getSubject().getPrincipal();//获取登录用户的信息（包括登录用户名和密码）
        request.setAttribute("username",admins.getUsername());
        request.setAttribute("admin",iAdminService.getAdminByUserName(admins.getUsername()));
        return "/admin/admin_reset";
    }

    @RequestMapping("/adminReset")
    public String adminReset(Admins admins,HttpServletRequest request){
        //修改密码操作
        //判断页面输入的原密码和新密码是否为null或者“”
        if (admins.getPassword() != null && !admins.getPassword().trim().isEmpty() && admins.getPasswordNew() != null && !admins.getPasswordNew().trim().isEmpty()){
            //修改密码了
            //判断原密码是否输入正确
            Admins admin = iAdminService.getAdminById(admins.getId());
            if (admin.getPassword().equals(SafeUtil.encode(admins.getPassword()))){
                //原密码输入正确
                //判断原密码和新密码是否一致
                if (admins.getPassword().equals(admins.getPasswordNew())){
                    request.setAttribute("msg","原密码和新密码不能一致");
                    return "forward:adminRe";
                }else{
                    //修改密码
                    //修改密码成功之后，退出重新登录
                    iAdminService.updatePasswordById(SafeUtil.encode(admins.getPasswordNew()),admin.getId());
                    return "redirect:logout";
                }
            }else{
                //原密码输入错误
                request.setAttribute("msg","原密码错误");
                return "forward:adminRe";
            }
        }

        request.setAttribute("msg","密码不能为空");
        return "forward:adminRe";
    }

    @RequestMapping("/orderList")
    public String orderList(
        @RequestParam(required = false,defaultValue = "0") int status,
        @RequestParam(required = false,defaultValue = "1") int page,
        HttpServletRequest request){

        request.setAttribute("flag",1);
        request.setAttribute("status",status);
        request.setAttribute("orderList",iAdminService.getOrderByStatus(status,page,10));
        request.setAttribute("pageTool", PageUtil.getPageTool(request,iAdminService.getOrderTotalByStatus(status),page,10));

        return "/admin/order_list";
    }

    @RequestMapping("/orderDispose")
    public String orderDispose(int id,int status,@RequestParam(required = false,defaultValue = "1") int page){

        //本质上就是改变订单的状态，发货按钮 -> 配送中
        iAdminService.dispose(id);

        return "redirect:orderList?status="+status+"&page="+page;
    }
}
