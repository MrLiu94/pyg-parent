package com.pyg.manager.controller;

import PageBean.PageResult;
import ReturnResult.Results;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbGoods;
import com.pyg.pojogroup.Goods;
import com.pyg.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 增加
     *
     * @param goods
     * @return
     */
    //    数据的更新和增加
    @RequestMapping("/save")
    public Results add(@RequestBody Goods goods) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.getGoods().setSellerId(name);
        try {
            goodsService.add(goods);
            return new Results(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Results update(@RequestBody Goods goods) {
        Goods one = goodsService.findOne(goods.getGoods().getId());
        //获取当前登录的商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!one.getGoods().getSellerId().equals(sellerId) || !goods.getGoods().getSellerId().equals(sellerId)) {
            return new Results(false, "非法操作");
        }
        try {
            goodsService.update(goods);
            return new Results(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false,"更新失败");
    }


}

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Results delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Results(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int size) {
        return goodsService.search(goods, page, size);
    }

    @RequestMapping("/updateStatus")
    public Results updateStatus(Long ids[],String status){
        try {
            goodsService.updateStatus(ids,status);
            return new Results(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false,"操作失败");
        }
    }

}
