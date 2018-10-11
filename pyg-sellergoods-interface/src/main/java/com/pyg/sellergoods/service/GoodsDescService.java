package com.pyg.sellergoods.service;
import java.util.List;
import com.pyg.pojo.TbGoodsDesc;

import PageBean.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsDescService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoodsDesc> findAll();
	

	
	
	/**
	 * 增加
	*/
	public boolean add(TbGoodsDesc goodsDesc);
	
	
	/**
	 * 修改
	 */
	public boolean update(TbGoodsDesc goodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoodsDesc findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param page 当前页 码
	 * @param rows 每页记录数
	 * @return
	 */
	PageResult search(TbGoodsDesc goodsDesc, int page, int rows);
}
