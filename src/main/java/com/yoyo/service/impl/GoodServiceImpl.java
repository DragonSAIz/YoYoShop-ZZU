package com.yoyo.service.impl;

import com.yoyo.dao.GoodDao;
import com.yoyo.entity.Goods;
import com.yoyo.entity.Tops;
import com.yoyo.entity.Types;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import com.yoyo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class GoodServiceImpl implements IGoodService {

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private ITypeService iTypeService;

    @Autowired
    private ITopService iTopService;

    @Override public Goods get(int id) {
        //调用dao,查询Good的数据
        Goods goods = goodDao.get(id);
        if(Objects.nonNull(goods)){
            Types types = iTypeService.get(goods.getTypeId());
            goods.setType(types);
        }
        return goods;
    }

    @Override public List<Goods> getListByType(Integer typeid, int page, int size) {
        //判断typeid是否大于0,如果大于0,根据类型查询相应数据;如果小于0,则查询全部类型数据
        if(typeid != null && typeid > 0){
            //调用dao,完成数据的查询操作
            return goodDao.getListByType(typeid,(page - 1) * size, size);
        }else {
            return goodDao.getTotalList((page - 1) * size, size);
        }

    }

    @Override public long getTotalSizeByType(Integer typeid) {
        //判断typeid是否不为null并且大于0,是,则查询对应类型总条数
        if(typeid != null && typeid > 0) {
            return goodDao.getTotalSizeByType(typeid);
        }else {
            return goodDao.getTotalSize();
        }
    }

    @Override public long getTotalSize(Integer typeid) {
        if(typeid != null && typeid > 0) {
            return goodDao.getTotalSizeByType(typeid);
        }else {
            return goodDao.getTotalSize();
        }
    }

    @Override public long getTotalSizeByName(String name) {
        return 0;
    }

    @Override public List<Goods> getList(Integer typeid, int page, int size) {
        if (typeid != null && typeid > 0) {
            //如果typeid不为null并且大于0,正常查询tops数据处理
            int status = typeid.intValue();
            List<Tops> topsList = iTopService.getList((byte)status, page, size);
            //页面需要展示goods数据,但是拿到的是tops的数据,所以需要从tops中取出goods数据返回
            if(topsList != null && !topsList.isEmpty()) {
                List<Goods> goodsList = new ArrayList<>();
                for(Tops tops:topsList){
                    Goods good = packTop(tops.getGood());
                    goodsList.add(good);
                }
                return goodsList;
            }
        }else {
            //如果typeid为null或者小于0,直接查询goods数据处理
            List<Goods> totalList = packTopList(goodDao.getTotalList((page - 1) * size, size));
            return totalList;
        }
        //保留,以防报错
        return null;
    }

    //接受一个goods的list,给list中的所有goods增加types数据,重新封装到goods的list中返回
    private  List<Goods> packTopList(List<Goods> list){
        for (Goods goods: list) {
            Integer typeId = goods.getTypeId();
            Types types = iTypeService.get(typeId);
            goods.setType(types);
            goods = packTop(goods);
        }
        return list;
    }

    //接收一个Goods,然后给Goods处理topSmall数据,然后返回
    private Goods packTop(Goods goods) {
        if (goods !=null) {
            //根据goods的id,查询tops表,将对应的tops数据查询出来
            List<Tops> topsList = iTopService.getListByGoodid(goods.getId());

            if (Objects.nonNull(topsList) && !topsList.isEmpty()){
                //获取查询的tops中的type,和实体类中的TYPE_SCROLL,TYPE_LARGE,TYPE_SMALL对比
                //对比成功,改goods中对应的topScroll,topLarge,topSmall数据为true
                for (Tops tops:topsList) {
                    if (tops.getType() == Tops.TYPE_SCROLL) {
                        goods.setTopScroll(true);
                    }else if (tops.getType() == Tops.TYPE_LARGE) {
                        goods.setTopLarge(true);
                    }else if (tops.getType() == Tops.TYPE_SMALL) {
                        goods.setTopSmall(true);
                    }
                }
            }
        }
        return goods;
    }

    @Override public List<Goods> getListByName(String name, int page, int size) {
        //调用dao,实现模糊查询数据
        return goodDao.getListByName(name, (page-1) * size, size);
    }
}
