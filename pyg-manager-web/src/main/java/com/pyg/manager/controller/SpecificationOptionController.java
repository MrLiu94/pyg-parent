package com.pyg.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.sellergoods.service.SpecificationOptionService;
import ReturnResult.Results;
import PageBean.PageResult;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

	@Reference
	private SpecificationOptionService specificationOptionService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecificationOption> findAll(){			
		return specificationOptionService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return specificationOptionService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/save")
	public Results save(@RequestBody TbSpecificationOption specificationOption){
		if (specificationOption.getId()!=null){
			try {
				specificationOptionService.add(specificationOption);
				return new Results(true, "增加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new Results(false, "增加失败");
			}
		}else {
			try {
				specificationOptionService.update(specificationOption);
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
	public TbSpecificationOption findOne(Long id){
		return specificationOptionService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Results delete(Long [] ids){
		try {
			specificationOptionService.delete(ids);
			return new Results(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Results(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param specificationOption
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSpecificationOption specificationOption, int page, int size  ){
		return specificationOptionService.findPage(specificationOption, page, size);
	}
	
}
