package com.yoyo.service;

import com.yoyo.entity.Types;

import java.util.List;

public interface ITypeService {

    /**
     * 查询type列表
     * @return
     */
    public List<Types> getList();

    /**
     * 根据id查询types信息
     * @param id
     * @return
     */
    public Types get(int id);
}
