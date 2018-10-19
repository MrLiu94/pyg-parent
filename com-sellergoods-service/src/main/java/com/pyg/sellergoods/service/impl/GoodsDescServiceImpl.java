package com.pyg.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbGoodsDescExample;
import com.pyg.pojo.TbGoodsDescExample.Criteria;
import com.pyg.sellergoods.service.GoodsDescService;

import PageBean.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Transactional
@Service
public class GoodsDescServiceImpl implements GoodsDescService {

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoodsDesc> findAll() {
		return goodsDescMapper.selectByExample(null);
	}


	/**
	 * 增加
	 */
	@Override
	public boolean add(TbGoodsDesc goodsDesc) {
		try {
			goodsDescMapper.insert(goodsDesc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public boolean update(TbGoodsDesc goodsDesc){
		try {
			goodsDescMapper.updateByPrimaryKey(goodsDesc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbGoodsDesc findOne(Long id){
		return goodsDescMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			goodsDescMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public PageResult search(TbGoodsDesc goodsDesc, int page, int rows) {
		PageHelper.startPage(page, rows);
		TbGoodsDescExample example=new TbGoodsDescExample();
		Criteria criteria = example.createCriteria();

		if(goodsDesc!=null){
			if(goodsDesc.getIntroduction()!=null && goodsDesc.getIntroduction().length()>0){
				criteria.andIntroductionLike("%"+goodsDesc.getIntroduction()+"%");
			}
			if(goodsDesc.getSpecificationItems()!=null && goodsDesc.getSpecificationItems().length()>0){
				criteria.andSpecificationItemsLike("%"+goodsDesc.getSpecificationItems()+"%");
			}
			if(goodsDesc.getCustomAttributeItems()!=null && goodsDesc.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+goodsDesc.getCustomAttributeItems()+"%");
			}
			if(goodsDesc.getItemImages()!=null && goodsDesc.getItemImages().length()>0){
				criteria.andItemImagesLike("%"+goodsDesc.getItemImages()+"%");
			}
			if(goodsDesc.getPackageList()!=null && goodsDesc.getPackageList().length()>0){
				criteria.andPackageListLike("%"+goodsDesc.getPackageList()+"%");
			}
			if(goodsDesc.getSaleService()!=null && goodsDesc.getSaleService().length()>0){
				criteria.andSaleServiceLike("%"+goodsDesc.getSaleService()+"%");
			}

		}

		Page<TbGoodsDesc> pages= (Page<TbGoodsDesc>)goodsDescMapper.selectByExample(example);
		return new PageResult(pages.getTotal(), pages.getResult());
	}


	
}
