package com.yoyo.controller;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Orders;
import com.yoyo.entity.Users;
import com.yoyo.service.IGoodService;
import com.yoyo.service.IOrderService;
import com.yoyo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/index")
public class GoodsController {

    @Autowired
    private ITypeService iTypeService;

    @Autowired
    private IGoodService iGoodService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/buy")
    @ResponseBody   //设置不再将返回值添加.jsp
    public String buy(int goodid, HttpServletRequest request, HttpSession session) {
        //判断是否登录
        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null) {
            //返回商品分类信息
            request.setAttribute("typeList", iTypeService.getList());
            //没有登录,跳转到登陆界面
            request.setAttribute("msg", "请先登录");
            request.setAttribute("flag", 6);
            return "login"; //只返回login字符串,不返回login.jsp
        }
        //用户已登录,添加商品到购物车
        //根据商品id,查询商品信息
        Goods goods = iGoodService.get(goodid);
        //判断商品的库存是否存在,存在则可以加入购物车;不存在则不可加入购物车
        if (goods.getStock() <= 0) {

            return "empty";
        }
        //库存存在
        //为了避免出现,新加入的商品回覆盖掉原来购物车中已有的商品
        //判断session中是否存储order信息,有说明已有商品,无说明没有商品
        Orders order = (Orders) session.getAttribute("order");
        if (order == null) {
            //购物车中没有商品,直接添加到购物车
            session.setAttribute("order", iOrderService.add(goods));
        }else {
            //购物车中已有商品,需在原数据基础上累计添加到购物车
            session.setAttribute("order", iOrderService.addOrderItem(order, goods));
        }

        return "ok";
    }

    @RequestMapping("/cart")
    public String cart (HttpServletRequest request) {
        request.setAttribute("typelist", iTypeService.getList());

        return "/index/cart";
    }
}
