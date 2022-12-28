package com.yoyo.dao;

import com.yoyo.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderDao {

    @Insert("insert into orders (total,amount,status,paytype,name,phone,address,systime,user_id) values (#{total},#{amount},#{status},#{paytype},#{name},#{phone},#{address},#{systime},#{userId})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public void save(Orders orders);

    @Select("SELECT * FROM orders WHERE id=#{orderid}")
    public Orders get(@Param("orderid") int orderid);

    @Update("update orders set status=#{status},paytype=#{paytype},name=#{name},phone=#{phone},address=#{address} where id=#{id}")
    public void updateOrderById(Orders orders);

    @Select("SELECT * FROM orders WHERE user_id=#{userId} ORDER BY id DESC")
    public List<Orders> getListByUserId(@Param("userId") int userId);
}
