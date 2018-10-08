package com.pyg.sellergoods.service;

import com.pyg.pojo.TbBrand;

import java.util.List;

//品牌接口
public interface BrandService {
    List<TbBrand> findAll();
}
