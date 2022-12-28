package com.yoyo.controller;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;
import com.yoyo.entity.Users;
import com.yoyo.service.IGoodService;
import com.yoyo.service.IOrderService;
import com.yoyo.service.ITypeService;
import com.yoyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/index")
public class GoodsController {

    @Autowired
    private ITypeService iTypeService;

    @Autowired
    private IGoodService iGoodService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IUserService iUserService;

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

    @RequestMapping("/lessen")
    @ResponseBody
    public String lessen(int goodid,HttpSession session){
        //判断是否登录
        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null){
            return "login";
        }
        //获取商品id对应的商品
        Goods goods = iGoodService.get(goodid);
        //从购物车中减去对应的商品
        //从session中获取购物车数据，从数据中减去对应的商品，再将新的购物车数据存储到session中
        Orders order = (Orders) session.getAttribute("order");
        if (order != null){
            Orders orders = iOrderService.lessenIndentItem(order, goods);
            session.setAttribute("order",orders);
        }

        return "ok";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deletes(int goodid,HttpSession session){
        //判断是否登录
        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null){
            return "login";
        }
        //将商品从购物车数据中删除
        Goods goods = iGoodService.get(goodid);

        Orders order = (Orders) session.getAttribute("order");
        if (order != null){
            Orders orders = iOrderService.deleteIndentItem(order, goods);
            session.setAttribute("order",orders);
        }

        return "ok";
    }

    @RequestMapping("/save")
    public String save(HttpSession session,HttpServletRequest request){
        request.setAttribute("typeList",iTypeService.getList());
        //判断用户是否登录
        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null){
            request.setAttribute("msg","请登录后提交订单");
            request.setAttribute("flag",6);
            return "/index/login";
        }
        //提交订单
        //从session中获取预订单信息
        Orders order = (Orders) session.getAttribute("order");
        if (order != null){
            //提交订单
            //判断数据库中商品的数量是否够下订单商品数量使用
            List<Items> itemList = order.getItemList();
            boolean isStock = false; // true：库存不足，false:库存充足
            String stockMsg = "";
            for (Items items:itemList) {
                //根据items中的商品的id,获取商品信息（商品的库存）
                Goods goods = iGoodService.get(items.getGoodId());
                if (items.getAmount() > goods.getStock()){ //购买的商品数量，大于商品的库存，不够提交订单，返回错误信息
                    stockMsg+="   商品：["+goods.getName()+"] 库存不足，当前库存数量："+goods.getStock();
                    isStock=true;
                }
            }
            if (isStock){
                request.setAttribute("msg",stockMsg);
                return "/index/cart";
            }
            //如果库存是充足，就可以进行下订单操作了
            //因为orders表需要保存users的相关信息，所以需要获取users的信息
            Users users = iUserService.get(userLogin.getId());
            order.setName(users.getName());
            order.setPhone(users.getPhone());
            order.setAddress(users.getAddress());
            order.setUserId(users.getId());
            order.setUser(users);
            //将orders的数据存储到数据库中
            int orderId = iOrderService.save(order);
            //清空购物车
            session.removeAttribute("order");
            //提交订单成功之后，需要重定向到/index/topay?orderid=28请求路径，同时需要将新增的order的id传递过去
            return "redirect:topay?orderid="+orderId;
        }
        //如果order==null
        request.setAttribute("msg","提交失败！请重新尝试");
        return "/index/cart";
    }


    @RequestMapping("/topay")
    public String topay(int orderid,HttpServletRequest request){
        //返回商品分类
        request.setAttribute("typeList",iTypeService.getList());
        //返回对应的订单的信息
        request.setAttribute("order",iOrderService.get(orderid));
        return "/index/pay";
    }

    @RequestMapping("/pay")
    public String pay(Orders orders){
        //支付操作
        iOrderService.pay(orders);
        return "redirect:payok?orderid="+orders.getId(); //支付成功，重定向到支付成功的页面，并将订单id传递给支付成功页面
    }

    @RequestMapping("/payok")
    public String payok(int orderid,HttpServletRequest request){
        request.setAttribute("typeList",iTypeService.getList());
        //在支付成功页面，需要根据订单的支付方式，显示不同的支付成功信息
        Orders orders = iOrderService.get(orderid);
        if (orders.getPaytype() == Orders.PAYTYPE_WECHAT || orders.getPaytype() == Orders.PAYTYPE_ALIPAY){
            request.setAttribute("msg","订单["+orderid+"]支付成功");
        }else{
            request.setAttribute("msg","订单["+orderid+"]货到付款");
        }

        return "/index/payok";
    }

    @RequestMapping("/order")
    public String order(HttpServletRequest request, HttpSession session) {
        //返回页面分类信息及选项选中的标识
        request.setAttribute("flag", 3);
        request.setAttribute("typelist", iTypeService.getList());
        //严谨性判断,判断用户是否登录
        Users userLogin = (Users) session.getAttribute("user");
        if (userLogin == null) {
            request.setAttribute("msg", "请登陆后查看订单");
            request.setAttribute("flag", 6);
            return "/index/login";
        }
        //查询对应用户的订单信息,展示到页面
        List<Orders> orderList = iOrderService.getListByUserId(userLogin.getId());
        if (orderList != null && !orderList.isEmpty()) {
            for (Orders orders:orderList) {
                //根据orderId查询items表的数据
                List<Items> itemList = iOrderService.getItemListByOrderId(orders.getId());
                orders.setItemList(itemList);
            }
        }
        request.setAttribute("orderList", orderList);
        return "/index/order";
    }
}
