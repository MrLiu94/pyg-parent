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



    @RequestMapping("/findOne")
    public TbBrand findOne(long id){
        return brandService.findOne(id);
    }


//    数据的更新和增加
    @RequestMapping("/save")
    public Results add(@RequestBody TbBrand brand){
            if (brand.getId()!=null){
                boolean flag=brandService.update(brand);
                if (flag){
                    return new Results(true,"更新成功");
                }else {
                    return new Results(false,"数据有误,更新失败");
                }
            }else {
                boolean flag = brandService.add(brand);
                if (flag){
                    return new Results(true,"增加成功");
                }else {
                    return new Results(false,"数据有误,增加失败");
                }
            }
    }
//    数据删除
    @RequestMapping("/delete")
    public Results delete(long[] ids){
        try {
            brandService.delete(ids);
            return new Results(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Results(false,"删除失败");
        }
    }
}

