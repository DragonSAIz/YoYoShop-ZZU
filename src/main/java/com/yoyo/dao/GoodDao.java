package com.yoyo.dao;

import com.yoyo.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodDao {

    @Select("SELECT * FROM goods WHERE id=#{id}")
    public Goods get(@Param("id") int id);

    @Select("SELECT * FROM goods WHERE type_id=#{typeid} ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Goods> getListByType(@Param("typeid") Integer typeid,@Param("page") int page,@Param("size") int size);

    @Select("SELECT * FROM goods ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Goods> getTotalList(@Param("page") int page,@Param("size") int size);

    @Select("SELECT COUNT(*) FROM goods WHERE type_id=#{typeid}")
    public long getTotalSizeByType(@Param("typeid") Integer typeid);

    @Select("SELECT COUNT(*) FROM goods")
    public long getTotalSize();

    @Select("SELECT COUNT(*) FROM goods WHERE NAME LIKE concat('%',#{name},'%')")
    public long getTotalSizeByName(@Param("name") String name);

    @Select("SELECT * FROM goods WHERE NAME LIKE concat('%',#{name},'%') ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Goods> getListByName(@Param("name") String name,@Param("page") int page,@Param("size") int size);

    @Update("UPDATE goods SET stock=#{stock} WHERE id=#{goodid}")
    public void updateStockById(@Param("stock") int stock,@Param("goodid") int goodid);
}
