package com.yoyo.controller;

import com.yoyo.entity.Tops;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import com.yoyo.service.ITypeService;
import com.yoyo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller     //让spring可以识别到controller,用来代替servlet
@RequestMapping("/index")       //设置controller的访问路径
public class IndexController {

    @Autowired
    private ITypeService iTypeService;

    @Autowired
    private ITopService iTopService;

    @Autowired
    private IGoodService iGoodService;

    @RequestMapping("/index")       //与controller的路径组合为 /index/index
    public String index(HttpServletRequest request){

        //获取页面需要展示的数据,返回给/index/index.jsp页面展示
        //flag  typelist
        request.setAttribute("flag",1);
        request.setAttribute("typeList", iTypeService.getList());
        //今日推荐top1List,热销推荐top2List,新品推荐top3List
        request.setAttribute("top1List", iTopService.getList(Tops.TYPE_SCROLL,1,1));
        request.setAttribute("top2List", iTopService.getList(Tops.TYPE_LARGE,1,6));
        request.setAttribute("top3List", iTopService.getList(Tops.TYPE_SMALL,1,8));

        return "/index/index";  //表示: /index/index.jsp
    }

    @RequestMapping("/goods")
    public String goods(Integer typeid, @RequestParam(required = false,defaultValue = "1") int page, HttpServletRequest request) {

        //查询数据,返回给goods.jsp展示
        request.setAttribute("flag", 2);
        //查询对应id的type数据,方便在goods.jsp页面展示type的名称数据
        if (typeid != null && typeid > 0){
            request.setAttribute("type", iTypeService.get(typeid));
        }
        //查询type分类列表数据,展示给页面
        request.setAttribute("typeList", iTypeService.getList());
        //查询返回对应的商品数据
        request.setAttribute("goodList", iGoodService.getListByType(typeid, page, 16));
        //返回分页条信息给页面
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotalSizeByType(typeid), page, 16));

        return "/index/goods";   //表示: /index/goods.jsp
    }

    @RequestMapping("/top")
    public String tops(Integer typeid, @RequestParam(required = false,defaultValue = "1") int page, HttpServletRequest request){
        //返回flag
        if (typeid != null) {
            request.setAttribute("flag", typeid==2 ? 7 : 8); //热销和新品业务逻辑是一样的
        }
        //返回商品分类数据typeList
        request.setAttribute("typeList", iTypeService.getList());
        //返回热销商品数据
        request.setAttribute("goodList", iGoodService.getList(typeid, page, 16));
        //返回分页工具条
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotalSize(typeid), page, 16));
        //查询数据,返回给goods.jsp展示
        return "/index/goods";  //表示: /index/goods.jsp
    }

    @RequestMapping("/search")
    public String search(String name, @RequestParam(required = false,defaultValue = "1") int page, HttpServletRequest request) {
        //返回商品分类数据typeList
        request.setAttribute("typeList",iTypeService.getList());
        //返回查找的商品数据
        //判断页面输入的内容不是null,并且长度不是0
        if (Objects.nonNull(name) && !name.trim().isEmpty()) {
            //返回搜索的数据
            request.setAttribute("goodList", iGoodService.getListByName(name.trim(), page, 16));
            //返回分页工具条
            request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotalSizeByName(name.trim()), page, 16));
        }

        return "/index/goods";  //表示: /index/goods.jsp
    }

    @RequestMapping("/detail")
    public String detail(Integer goodid, HttpServletRequest request) {
        //返回商品分类信息
        request.setAttribute("typeList", iTypeService.getList());
        if (goodid != null && goodid > 0){
            //返回对应的商品信息
            request.setAttribute("good", iGoodService.get(goodid));
        }

        return "/index/detail"; //表示: /index/detail.jsp
    }
}
