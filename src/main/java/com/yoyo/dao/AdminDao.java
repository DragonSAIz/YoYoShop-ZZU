package com.yoyo.dao;

import com.yoyo.entity.Admins;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

public interface AdminDao {

    @Select("SELECT * FROM admins WHERE username=#{userName}")
    public Admins getAdminByUserName(@Param("userName") String userName);
}
