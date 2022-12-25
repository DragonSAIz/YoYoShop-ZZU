package com.yoyo.dao;

import com.yoyo.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDao {

    @Select("SELECT * FROM users WHERE username=#{userName}")
    public Users isExist(@Param("userName") String userName);

    @Insert("INSERT INTO users (username,password,name,phone,address) values (#{username},#{password},#{name},#{phone},#{address})")
    public void add(Users users);

    @Select("SELECT * FROM users WHERE username=#{userName} AND PASSWORD=#{passWord}")
    public Users checkUser(@Param("userName") String userName, @Param("passWord") String passWord);

    @Select("SELECT * FROM users WHERE username=#{userName} AND phone=#{Phone}")
    public Users check(@Param("userName") String userName, @Param("Phone") String Phone);

    @Update("UPDATE users SET PASSWORD=#{passWord} WHERE username=#{userName} AND phone=#{Phone}")
    public void updatePasswordByUserNameAndPhone(@Param("passWord") String passWord,@Param("userName") String userName,@Param("Phone") String Phone);

    @Select("SELECT * FROM users WHERE id=#{id}")
    public Users get(@Param("id") int id);

    @Update("UPDATE users SET name=#{name},phone=#{phone},address=#{address} WHERE id=#{id}")
    public void updateUserByid(@Param("id") int id,@Param("name") String name,@Param("phone") String phone,@Param("address") String address);

    @Update("UPDATE users SET password=#{newPassWord} WHERE id=#{id}")
    public void updatePassWordById(@Param("id") int id,@Param("newPassWord") String newPassWord);
}
