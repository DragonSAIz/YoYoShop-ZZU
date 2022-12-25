package com.yoyo.service;

import com.yoyo.entity.Users;

public interface IUserService {

    /**
     * 判断数据库中是否有同名用户
     * @param userName
     * @return
     */
    public boolean isExist(String userName);

    /**
     * 添加用户信息
     * @param users
     */
    public void add(Users users);

    /**
     * 检查用户名和密码
     * @param userName
     * @param passWord
     * @return
     */
    public Users checkUser(String userName, String passWord);

    /**
     * 检查用户名和手机号
     * @param userName
     * @param Phone
     * @return
     */
    public Users check(String userName, String Phone);

    /**
     * 根据用户名和手机号重置密码
     * @param passWord
     * @param userName
     * @param Phone
     */
    public void updatePasswordByUserNameAndPhone(String passWord, String userName, String Phone);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    public Users get(int id);

    /**
     * 根据用户id修改姓名,电话和地址
     * @param id
     * @param name
     * @param phone
     * @param address
     */
    public void updateUserByid(int id, String name, String phone, String address);

    /**
     * 根据用户id修改用户密码
     * @param id
     * @param newPassWord
     */
    public void updatePassWordById(int id, String newPassWord);
}
