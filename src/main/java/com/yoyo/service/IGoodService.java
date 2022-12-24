package com.yoyo.service;

import com.yoyo.entity.Goods;

import java.util.List;

public interface IGoodService {

    /**
     * 根据商品id,查询商品信息
     * @param id
     * @return
     */
    public Goods get(int id);

    /**
     * 根据类型id分页查询对应的商品信息
     * @param typeid    类型id
     * @param page  查询的分页页码
     * @param size  查询的每页显示条数
     * @return
     */
    public List<Goods> getListByType(Integer typeid, int page, int size);

    /**
     * 根据类型获取总条数 商品分类
     * @param typeid
     * @return
     */
    public long getTotalSizeByType(Integer typeid);

    /**
     * 获取热销商品总条数
     * @param typeid
     * @return
     */
    public long getTotalSize(Integer typeid);

    /**
     * 获取搜索数据总条数
     * @param name
     * @return
     */
    public long getTotalSizeByName(String name);

    /**
     * 获取热销商品数据
     * @param typeid
     * @param page
     * @param size
     * @return
     */
    public List<Goods> getList(Integer typeid, int page, int size);

    /**
     * 搜索数据
     * @param name
     * @param page
     * @param size
     * @return
     */
    public List<Goods> getListByName(String name, int page, int size);
}
