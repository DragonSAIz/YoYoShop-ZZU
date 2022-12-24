package com.yoyo.service;

import com.yoyo.entity.Tops;

import java.util.List;

public interface ITopService {

    /**
     * 查询今日推荐,热销推荐,新品推荐
     * @param type  查询类型
     * @param page  查询的起始位置
     * @param size  查询的条数
     * @return
     */
    public List<Tops> getList(byte type, int page, int size);

    /**
     * 根据商品的id,查询tops数据
     * @param goodid
     * @return
     */
    public List<Tops> getListByGoodid(int goodid);
}
