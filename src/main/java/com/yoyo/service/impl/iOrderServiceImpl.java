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

    @Override public Orders addOrderItem(Orders orders, Goods goods) {
        List<Items> itemsList = orders.getItemList();
        itemsList = itemsList==null ? new ArrayList<Items>() : itemsList;
        boolean noThis = true; //true:商品不存在购物车中;false:在购物车中
        for (Items items:itemsList) {
            if (items.getGoodId() == goods.getId()) {
                //原数量加一
                items.setAmount(items.getAmount()+1);
                items.setTotal(items.getPrice() * items.getAmount());
                noThis = false;
            }
        }
        if (noThis == true) {
            itemsList.add(addItems(goods));
        }

        orders.setAmount(orders.getAmount() + 1);
        orders.setTotal(orders.getTotal() + goods.getPrice());

        return orders;
    }

}
