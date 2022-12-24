package com.yoyo.dao;

import com.yoyo.entity.Types;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypeDao {

    @Select("SELECT * FROM TYPES ORDER BY id DESC")
    public List<Types> getList();

    @Select("SELECT * FROM TYPES WHERE id=#{id}")
    public Types get(@Param("id") int id);
}
