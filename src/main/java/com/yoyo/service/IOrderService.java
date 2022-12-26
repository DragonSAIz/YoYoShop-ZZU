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
}
