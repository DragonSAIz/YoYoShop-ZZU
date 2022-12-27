package com.yoyo.dao;

import com.yoyo.entity.Items;
import org.apache.ibatis.annotations.Insert;

public interface ItemDao {

    @Insert("insert into items (price,amount,order_id,good_id) values (#{price},#{amount},#{orderId},#{goodId})")
    public void save(Items items);

}
