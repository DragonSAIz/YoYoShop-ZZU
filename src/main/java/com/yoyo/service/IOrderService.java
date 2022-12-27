package com.yoyo.service;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;

public interface IOrderService {

    /**
     * 购物车为空时,直接添加商品到购物车
     * @return
     */
    public Orders add(Goods goods);

    /**
     * 将商品Goods添加进Items
     * @param goods
     * @return
     */
    public Items addItems(Goods goods);

    /**
     * 将商品数据,储存到已有商品的购物车数据中
     * @param orders
     * @param goods
     * @return
     */
    public Orders addOrderItem(Orders orders, Goods goods);

    /**
     * 购物车减去商品操作
     * @param orders
     * @param goods
     * @return
     */
    public Orders lessenIndentItem(Orders orders,Goods goods);

    /**
     * 购物车删除商品操作
     * @param orders
     * @param goods
     * @return
     */
    public Orders deleteIndentItem(Orders orders,Goods goods);

    /**
     * 保存orders表和items表的数据，实现提交订单操作
     * @param orders
     * @return  返回新增的orderid
     */
    public int save(Orders orders);

    /**
     * 根据订单id,查询订单信息
     * @param orderid
     * @return
     */
    public Orders get(int orderid);

    /**
     * 对对应的订单进行支付操作
     * @param orders
     */
    public void pay(Orders orders);
}
