package com.yoyo.service;

import com.yoyo.entity.Admins;

public interface IAdminService {

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public Admins getAdminByUserName(String userName);
}
