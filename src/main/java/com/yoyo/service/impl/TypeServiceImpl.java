package com.yoyo.service.impl;

import com.yoyo.dao.TypeDao;
import com.yoyo.entity.Types;
import com.yoyo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //让spring识别
@Transactional //事务操作
public class TypeServiceImpl implements ITypeService {

    @Autowired //注入TypeDao
    private TypeDao typeDao;

    @Override public List<Types> getList() {

        //调用Dao,获取数据库数据
        return typeDao.getList();
    }

    @Override public Types get(int id) {

        return typeDao.get(id);
    }
}
