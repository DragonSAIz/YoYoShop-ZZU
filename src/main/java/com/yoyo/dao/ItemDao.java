package com.yoyo.dao;

import com.yoyo.entity.Items;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemDao {

    @Insert("insert into items (price,amount,order_id,good_id) values (#{price},#{amount},#{orderId},#{goodId})")
    public void save(Items items);

    @Select("SELECT *FROM items WHERE order_id=#{orderId}")
    public List<Items> getItemsByOrderId(@Param("orderId") int orderId);
}
