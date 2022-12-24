package com.yoyo.service.impl;

import com.yoyo.dao.TopDao;
import com.yoyo.entity.Goods;
import com.yoyo.entity.Tops;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

@Service
@Transactional
public class TopServiceImpl implements ITopService {

    @Autowired
    private TopDao topDao;

    @Autowired
    private IGoodService iGoodService;

    @Override public List<Tops> getList(byte type, int page, int size) {
        //调用dao查询tops表数据    查询起始位置= (page-1) * size
        List<Tops> list = topDao.getList(type,(page - 1) * size,size);

        //上面的拆线呢操作知识查询了商品id,没有查询到商品,所以需要单独查询商品
        for(Tops tops:list) {
            //top.getGoodId() //商品id
            //根据商品id查询商品具体信息
            Goods goods = iGoodService.get(tops.getGoodId());
            tops.setGood(goods);
        }

        return list;
    }

    @Override public List<Tops> getListByGoodid(int goodid) {
        //调用dao查询数据
        return topDao.getListByGoodid(goodid);
    }
}
