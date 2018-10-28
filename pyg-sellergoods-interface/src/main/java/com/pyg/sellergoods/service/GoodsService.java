package com.pyg.sellergoods.service;

import PageBean.PageResult;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbItem;
import com.pyg.pojogroup.Goods;

import java.util.List;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbGoods> findAll();


    //更新数据
    void update(Goods goods);

    //    新增数据
    void add(Goods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Goods findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    PageResult search(TbGoods goods, int pageNum, int pageSize);

    //审核状态更新
    public void updateStatus(Long[] ids, String status);

    /**
     * 根据商品 ID 和状态查询 Item 表信息
     * @param goodsIds
     * @param status
     * @return
     */
    public List<TbItem> findItemListByGoodsIdandStatus(Long[] goodsIds, String status );

}
