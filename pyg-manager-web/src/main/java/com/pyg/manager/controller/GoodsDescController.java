package com.pyg.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.sellergoods.service.GoodsDescService;
import ReturnResult.Results;
import PageBean.PageResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

	@Reference
	private GoodsDescService goodsDescService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoodsDesc> findAll(){			
		return goodsDescService.findAll();
	}

	/**
	 * 增加&更新
	 * @param goodsDesc
	 * @return
	 */
	@RequestMapping("/save")
	public Results save(@RequestBody TbGoodsDesc goodsDesc){
		if (goodsDesc.getGoodsId()!=null){
			boolean flag=goodsDescService.update(goodsDesc);
			if (flag){
				return new Results(true,"更新成功");
			}else {
				return new Results(false,"数据有误,更新失败");
			}
		}else {
			boolean flag = goodsDescService.add(goodsDesc);
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
	public TbGoodsDesc findOne(Long id){
		return goodsDescService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(Long [] ids){
		try {
			goodsDescService.delete(ids);
			return new Results(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param goodsDesc
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoodsDesc goodsDesc, int page, int size  ){
		return goodsDescService.search(goodsDesc, page, size);
	}
	
}
