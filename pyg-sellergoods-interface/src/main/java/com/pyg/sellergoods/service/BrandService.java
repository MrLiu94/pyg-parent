package com.pyg.sellergoods.service;

import PageBean.PageResult;
import com.pyg.pojo.TbBrand;

import java.util.List;
import java.util.Map;

//品牌接口
public interface BrandService {
    List<TbBrand> findAll();
//    实现分页
    PageResult findPage(int page,int size);
//   添加数据
    boolean add(TbBrand brand);

    TbBrand findOne(long id);

    boolean update(TbBrand brand);

    void delete(long[] ids);

    PageResult search(TbBrand brand, int page, int size);

    List<Map> selectOptionList();
}
