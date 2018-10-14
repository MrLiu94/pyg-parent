package com.pyg.manager.controller;

import PageBean.PageResult;
import ReturnResult.Results;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbSeller;
import com.pyg.sellergoods.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/save")
	public Results add(@RequestBody TbSeller seller){

		if (seller.getSellerId()!=null){
			try {
				sellerService.add(seller);
				return new Results(true, "增加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "申请失败");
			}
		}
		return new Results(false,"登陆名不能为空,申请失败");
	}

	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Results(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param seller
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int size  ){
		return sellerService.findPage(seller, page, size);
	}

	@RequestMapping("/updateStatus")
	public Results updateStatus(String sellerId,String status){
		System.out.println(sellerId+" "+status);
		try {
			sellerService.updateStatus(sellerId,status);

			return new Results(true,"更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false,"更新失败");
		}
	}
	
}
