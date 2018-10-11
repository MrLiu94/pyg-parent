package com.pyg.sellergoods.service.impl;

import PageBean.PageResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbBrandExample;
import com.pyg.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<TbBrand> tbBrands = (Page<TbBrand>) brandMapper.selectByExample(null);
        return new PageResult(tbBrands.getTotal(), tbBrands.getResult());
    }



    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public boolean add(TbBrand brand) {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(brand.getName());
        List<TbBrand> tbBrands = brandMapper.selectByExample(example);
        if (tbBrands.size() > 0) {
            return false;
        } else {
            brandMapper.insert(brand);
            return true;
        }

    }

    //数据回显
    @Override
    public TbBrand findOne(long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    //数据更新
    @Override
    public boolean update(TbBrand brand) {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(brand.getName());
        List<TbBrand> tbBrands = brandMapper.selectByExample(example);
        if (tbBrands.size() > 0) {
            return false;
        } else {
            brandMapper.updateByPrimaryKey(brand);
            return true;
        }
    }
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    @Override
    public void delete(long[] ids) {
        for (long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult search(TbBrand brand, int page, int size) {
        PageHelper.startPage(page, size);
        TbBrandExample example=new TbBrandExample();
        if (brand!=null){
            TbBrandExample.Criteria criteria = example.createCriteria();
            if (brand.getName()!=null&&brand.getName().length()>0){
                criteria.andNameLike("%"+brand.getName()+"%");
            }
            if (brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }
        Page<TbBrand> tbBrands = (Page<TbBrand>) brandMapper.selectByExample(example);
        return new PageResult(tbBrands.getTotal(), tbBrands.getResult());
    }

}
