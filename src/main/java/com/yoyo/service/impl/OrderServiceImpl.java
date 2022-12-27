package com.yoyo.service.impl;

import com.yoyo.dao.ItemDao;
import com.yoyo.dao.OrderDao;
import com.yoyo.entity.Goods;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;
import com.yoyo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ItemDao itemDao;

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

    @Override public Orders lessenIndentItem(Orders orders, Goods goods) {
        //Goods存储到Items,Items存储到List中，List存储到Orders
        List<Items> itemList = orders.getItemList();
        itemList = itemList==null ? new ArrayList<Items>() : itemList;
        boolean noThis=true;// true:商品不存在购物车中    false:存在
        //存在操作
        for (Items items:itemList) {
            if (items.getGoodId() == goods.getId()){
                //缺陷：如果数量减到0了，表示商品就没有了，需要将商品从购物车数据中删除
                if (items.getAmount()-1 <= 0){
                    return deleteIndentItem(orders,goods);
                }
                items.setAmount(items.getAmount()-1);
                items.setTotal(items.getPrice() * items.getAmount());
                noThis=false;
            }
        }
        //不存在操作
        if (noThis){
            //如果商品不在购物车数据中，那是不用做减操作
            return orders;
        }
        orders.setAmount(orders.getAmount()-1);
        orders.setTotal(orders.getTotal() - goods.getPrice());
        return orders;
    }

    @Override public Orders deleteIndentItem(Orders orders, Goods goods) {
        //Goods存储到Items,Items存储到List中，List存储到Orders
        List<Items> itemList = orders.getItemList();
        itemList = itemList==null ? new ArrayList<Items>() : itemList;
        boolean noThis=true;// true:商品不存在购物车中    false:存在
        int itemAmount=0; //存储要删除的商品的数量，方便后面计算购物车总数量
        List<Items> resultList = new ArrayList<>();//保存除了删除商品之外的其他商品
        //存在操作
        for (Items items:itemList) {
            if (items.getGoodId() == goods.getId()){ //商品一致
                //如果items中的goods和要删除的goods对应上了，说明就是要删除的数据
                itemAmount = items.getAmount();
                noThis = false;
                continue; //到这就跳出本次循环，不在往下执行，继续开始下一次循环
            }
            //商品不一致
            resultList.add(items);
        }
        //如果resultList没有存储数据了,说明购物车中本来就是只有一个商品，还被删除了，购物车就不存在了
        if (resultList.isEmpty()){
            return  null;
        }
        //将新的购物车list数据，存储到orders中，替换原来的购物车list数据
        orders.setItemList(resultList);
        //不存在操作
        if (noThis){
            //如果商品不在购物车数据中，那是不用做删除操作
            return orders;
        }
        orders.setAmount(orders.getAmount()-itemAmount);
        orders.setTotal(orders.getTotal() - (goods.getPrice()*itemAmount));
        return orders;
    }

    @Override public int save(Orders orders) {
        //先保存orders表的数据
        //设置状态和提交订单时间数据
        orders.setStatus(Orders.STATUS_UNPAY); //设置订单状态为未付款
        orders.setSystime(new Date());
        //调用dao，保存数据
        orderDao.save(orders);
        Integer orderId = orders.getId(); //获取到新增的Order的id
        //再保存items表的数据，需要用到新增的orders数据的id
        for (Items items:orders.getItemList()) {
            //取出一个items,调用dao操作保存一次数据
            items.setOrderId(orderId);
            itemDao.save(items);
        }
        return orderId;
    }

    @Override public Orders get(int orderid) {
        //调用dao
        return orderDao.get(orderid);
    }

    @Override public void pay(Orders orders) {
        //因为微信支付、支付宝支付需要使用第三方平台，这边不做，用走数据库的方式代替
        //数据库操作就做一个操作：更新订单的支付方式、支付状态、以及用户在页面更改的收货人信息、电话、地址
        //1.先根据页面传递的订单的id,查询订单信息
        Orders order = orderDao.get(orders.getId());
        if (order != null){
            order.setPaytype(orders.getPaytype());
            //如果支付方式是微信支付或者支付宝支付，支付状态为已支付，如果是货到付款，支付状态为配送中
            if (orders.getPaytype() == Orders.PAYTYPE_WECHAT || orders.getPaytype() == Orders.PAYTYPE_ALIPAY){
                order.setStatus(Orders.STATUS_PAYED);
            }else{
                order.setStatus(Orders.STATUS_SEND);
            }
            order.setName(orders.getName());
            order.setPhone(orders.getPhone());
            order.setAddress(orders.getAddress());
            //更新数据库数据
            orderDao.updateOrderById(order);
            //支付成功之后，修改goods表中对应的商品的库存
        }
    }
}
