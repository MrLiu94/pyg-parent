package com.pyg.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbItemCat;
import com.pyg.sellergoods.service.ItemCatService;
import ReturnResult.Results;
import PageBean.PageResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){			
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/save")
	public Results save(@RequestBody TbItemCat itemCat){
		if (itemCat.getId()!=null){
			try {
				itemCatService.update(itemCat);
				return new Results(true, "修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "修改失败");
			}
		}else {
			try {
				itemCatService.add(itemCat);
				return new Results(true, "增加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "增加失败");
			}
		}
	}

	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbItemCat findOne(Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(Long [] ids){
		try {
			itemCatService.delete(ids);
			return new Results(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param itemCat
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbItemCat itemCat, int page, int size  ){
		return itemCatService.findPage(itemCat, page, size);
	}
	
}
