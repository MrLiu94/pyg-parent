package com.pyg.manager.controller;

import PageBean.PageResult;
import ReturnResult.Results;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbBrand;
import com.pyg.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    @RequestMapping("/findAll")//查询所有品牌信息
    public List<TbBrand> findAll() {

        return brandService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page,int size){
        return brandService.findPage(page, size);
    }

//    添加数据
    @RequestMapping("/add")
    public Results add(@RequestBody TbBrand brand){
            boolean flag = brandService.add(brand);
            if (flag){
                return new Results(true, "增加成功");
            }else {
                return new Results(false,"增加失败 品牌已存在");
            }

    }
}

