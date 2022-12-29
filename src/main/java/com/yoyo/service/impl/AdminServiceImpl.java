package com.yoyo.service.impl;

import com.yoyo.dao.AdminDao;
import com.yoyo.dao.OrderDao;
import com.yoyo.entity.Admins;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;
import com.yoyo.entity.Users;
import com.yoyo.service.IAdminService;
import com.yoyo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private OrderDao orderDao;

    @Override public Admins getAdminByUserName(String userName) {
        //调用Dao
        return adminDao.getAdminByUserName(userName);
    }

    @Override public Admins getAdminById(int id) {
        //调用Dao
        return adminDao.getAdminById(id);
    }

    @Override public void updatePasswordById(String password, int id) {
        //调用Dao
        adminDao.updatePasswordById(password,id);
    }

    @Override public List<Orders> getOrderByStatus(int status, int page, int size) {
        //如果status为0，表示查询所有的订单信息
        //如果status不为0，表示status就是1、2、3、4，根据状态查询订单信息
        List<Orders> orderList;
        if (status == 0){
            orderList = adminDao.getOrderList((page - 1) * size, size);
        }else{
            orderList = adminDao.getOrderListByStatus(status, (page - 1) * size, size);
        }
        //还需要查询下单用户和订单对应的商品信息
        if (orderList != null && !orderList.isEmpty()) {
            for (Orders orders:orderList) {
                //查询下单用户数据
                Users users = adminDao.getUsersById(orders.getUserId());
                orders.setUser(users);
                //查询订单对应的商品信息
                //先根据orderid查询items表数据，然后根据goodid查询goods表数据
                List<Items> itemList = iOrderService.getItemListByOrderId(orders.getId());
                orders.setItemList(itemList);
            }
        }
        return orderList;
    }

    @Override public long getOrderTotalByStatus(int status) {
        //如果status为0，表示查询全部订单总条数
        //如果status不为0，为1、2、3、4，表示查询对应状态的订单的总条数
        long total;
        if (status == 0) {
            total = adminDao.getOrderTotal();
        }else {
            total = adminDao.getOrderTotalByStatus(status);
        }
        return total;
    }

    @Override public void dispose(int orderid) {
        //根据orderid查询订单的信息，将状态更改为配送中
        Orders orders = orderDao.get(orderid);
        orders.setStatus(Orders.STATUS_SEND);
        adminDao.updateOrderStatus(orders);
    }
}
