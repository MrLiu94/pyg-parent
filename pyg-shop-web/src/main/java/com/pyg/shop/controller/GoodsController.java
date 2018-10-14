package com.pyg.shop.controller;

import PageBean.PageResult;
import ReturnResult.Results;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbGoods;
import com.pyg.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}



	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	//    数据的更新和增加
	@RequestMapping("/save")
	public Results save(@RequestBody TbGoods goods){
		if (goods.getId()!=null){
			boolean flag=goodsService.update(goods);
			if (flag){
				return new Results(true,"更新成功");
			}else {
				return new Results(false,"数据有误,更新失败");
			}
		}else {
			boolean flag = goodsService.add(goods);
			if (flag){
				return new Results(true,"增加成功");
			}else {
				return new Results(false,"数据有误,增加失败");
			}
		}
	}

	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbGoods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(Long [] ids){
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
	 * @param goods
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods,int page, int size ){
		return goodsService.search(goods, page, size);
	}
	
}
