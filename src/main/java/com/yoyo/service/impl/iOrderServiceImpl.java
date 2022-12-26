package com.yoyo.service.impl;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;
import com.yoyo.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class iOrderServiceImpl implements IOrderService {
    @Override public Orders add(Goods goods) {

        List<Items> itemsList = new ArrayList<>();
        itemsList.add(addItems(goods));    //将items添加到List

        //将Goods存储到Items,Items存储到List中,List存储到Orders
        Orders orders = new Orders();
        orders.setItemList(itemsList);
        orders.setTotal(goods.getPrice());  //购物车商品总价
        orders.setAmount(1);    //购物车中无商品,故添加购物车商品数量为1

        return orders;
    }

    @Override public Items addItems(Goods goods) {
        Items items = new Items();
        items.setGoodId(goods.getId());
        items.setAmount(1);
        items.setPrice(goods.getPrice());
        items.setTotal(goods.getPrice());
        items.setGood(goods);
        return null;
    }

}
