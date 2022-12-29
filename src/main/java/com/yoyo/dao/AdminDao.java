package com.yoyo.dao;

import com.yoyo.entity.Admins;
import com.yoyo.entity.Orders;
import com.yoyo.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminDao {

    @Select("SELECT * FROM admins WHERE username=#{userName}")
    public Admins getAdminByUserName(@Param("userName") String userName);

    @Select("SELECT * FROM admins WHERE id=#{id}")
    public Admins getAdminById(@Param("id") int id);

    @Update("update admins set password=#{password} where id=#{id}")
    public void updatePasswordById(@Param("password") String password,@Param("id") int id);

    @Select("SELECT * FROM orders ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Orders> getOrderList(@Param("page") int page,@Param("size") int size);

    @Select("SELECT * FROM orders WHERE STATUS=#{status} ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Orders> getOrderListByStatus(@Param("status") int status,@Param("page") int page,@Param("size") int size);

    @Select("SELECT * FROM users WHERE id=#{id}")
    public Users getUsersById(@Param("id") int id);

    @Select("SELECT COUNT(*) FROM orders")
    public long getOrderTotal();

    @Select("SELECT COUNT(*) FROM orders  WHERE STATUS=#{status}")
    public long getOrderTotalByStatus(@Param("status") int status);

    @Update("update orders set status=#{status} where id=#{id}")
    public void updateOrderStatus(Orders orders);
}
