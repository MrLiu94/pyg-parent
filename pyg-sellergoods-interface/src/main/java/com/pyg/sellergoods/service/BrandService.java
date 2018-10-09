package com.pyg.sellergoods.service;

import PageBean.PageResult;
import com.pyg.pojo.TbBrand;

import java.util.List;

//品牌接口
public interface BrandService {
    List<TbBrand> findAll();
//    实现分页
    PageResult findPage(int page,int size);
//   添加数据
    void add(TbBrand brand);
}
