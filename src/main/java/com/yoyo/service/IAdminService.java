package com.yoyo.service;

import com.yoyo.entity.Admins;
import com.yoyo.entity.Orders;

import java.util.List;

public interface IAdminService {

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public Admins getAdminByUserName(String userName);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public Admins getAdminById(int id);

    /**
     * 根据id，更新用户的密码
     * @param password
     * @param id
     */
    public void updatePasswordById(String password,int id);

    /**
     * 根据状态分页查询订单信息
     * @param status
     * @param page
     * @param size
     * @return
     */
    public List<Orders> getOrderByStatus(int status,int page,int size);

    /**
     * 根据状态查询订单总条数
     * @param status
     * @return
     */
    public long getOrderTotalByStatus(int status);

    /**
     * 发货操作：更改订单状态为配送中
     * @param orderid
     */
    public void dispose(int orderid);
}
