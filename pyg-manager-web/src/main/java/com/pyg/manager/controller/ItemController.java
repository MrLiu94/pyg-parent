package com.pyg.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbItem;
import com.pyg.sellergoods.service.ItemService;
import ReturnResult.Results;
import PageBean.PageResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Reference
	private ItemService itemService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItem> findAll(){			
		return itemService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return itemService.findPage(page, rows);
	}


	/**
	 * 增加
	 * @param item
	 * @return
	 */
	@RequestMapping("/save")
	public Results save(@RequestBody TbItem item){
		if (item.getId()!=null){
			try {
				itemService.add(item);
				return new Results(true, "增加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "增加失败");
			}
		}else {
			try {
				itemService.update(item);
				return new Results(true, "修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "修改失败");
			}
		}
	}

	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbItem findOne(Long id){
		return itemService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(Long [] ids){
		try {
			itemService.delete(ids);
			return new Results(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param item
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbItem item, int page, int size  ){
		return itemService.findPage(item, page, size);
	}
	
}
