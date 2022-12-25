package com.yoyo.dao;

import com.yoyo.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("SELECT * FROM users WHERE username=#{userName}")
    public Users isExist(@Param("userName") String userName);

    @Insert("INSERT INTO users (username,password,name,phone,address) values (#{username},#{password},#{name},#{phone},#{address})")
    public void add(Users users);

    @Select("SELECT * FROM users WHERE username=#{userName} AND PASSWORD=#{passWord}")
    public Users checkUser(@Param("userName") String userName, @Param("passWord") String passWord);

    @Select("SELECT * FROM users WHERE username=#{userName} AND phone=#{Phone}")
    public Users check(@Param("userName") String userName, @Param("Phone") String Phone);
}
