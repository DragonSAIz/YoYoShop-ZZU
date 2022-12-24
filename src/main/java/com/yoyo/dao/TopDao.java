package com.yoyo.dao;

import com.yoyo.entity.Tops;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TopDao {

    @Select("SELECT * FROM tops WHERE TYPE=#{type} ORDER BY id DESC LIMIT #{page},#{size}")
    public List<Tops> getList(@Param("type") byte type, @Param("page") int page, @Param("size") int size);

    @Select("SELECT * FROM tops WHERE good_id=#{goodid} ORDER BY id DESC")
    public List<Tops> getListByGoodid(@Param("goodid") int goodid);
}
