package com.pyg.sellergoods.service;

import java.util.List;

import com.pyg.pojo.TbGoods;

import PageBean.PageResult;

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
    boolean update(TbGoods goods);

    //    新增数据
    boolean add(TbGoods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbGoods findOne(Long id);


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

}
