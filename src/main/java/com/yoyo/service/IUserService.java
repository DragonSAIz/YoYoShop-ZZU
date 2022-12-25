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
}
