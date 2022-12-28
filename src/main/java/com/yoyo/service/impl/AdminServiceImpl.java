package com.yoyo.service.impl;

import com.yoyo.dao.AdminDao;
import com.yoyo.entity.Admins;
import com.yoyo.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminDao adminDao;

    @Override public Admins getAdminByUserName(String userName) {
        //调用dao
        return adminDao.getAdminByUserName(userName);
    }
}
