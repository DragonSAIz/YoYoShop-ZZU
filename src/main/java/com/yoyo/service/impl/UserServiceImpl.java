package com.yoyo.service.impl;

import com.yoyo.dao.UserDao;
import com.yoyo.entity.Users;
import com.yoyo.service.IUserService;
import com.yoyo.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override public boolean isExist(String userName) {
        //调用dao
        Users users = userDao.isExist(userName);
        return users != null;   //有同名为true,不可注册;无同名为false,可以注册
    }

    @Override public void add(Users users) {
        //往数据库中储存用户数据时,进行密码加密
        String encode = SafeUtil.encode(users.getPassword());
        users.setPassword(encode);
        //调用dao
        userDao.add(users);
    }

    @Override public Users checkUser(String userName, String passWord) {
        //调用dao
        return userDao.checkUser(userName, SafeUtil.encode(passWord));
    }

    @Override public Users check(String userName, String Phone) {
        //调用dao
        return userDao.check(userName, Phone);
    }

    @Override public void updatePasswordByUserNameAndPhone(String passWord, String userName, String Phone) {
        //对密码进行加密
        String encode = SafeUtil.encode(passWord);
        //调用dao
        userDao.updatePasswordByUserNameAndPhone(encode, userName, Phone);
    }

    @Override public Users get(int id) {
        //调用dao
        return userDao.get(id);
    }

    @Override public void updateUserByid(int id, String name, String phone, String address) {
        //调用dao
        userDao.updateUserByid(id, name, phone, address);
    }

    @Override public void updatePassWordById(int id, String newPassWord) {
        String encode = SafeUtil.encode(newPassWord);
        //调用dao
        userDao.updatePassWordById(id, encode);
    }

}
