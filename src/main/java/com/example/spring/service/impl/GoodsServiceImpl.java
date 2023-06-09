package com.example.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.entity.Goods;
import com.example.spring.mapper.GoodsMapper;
import com.example.spring.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2023-04-27
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public IPage pageC(IPage<Goods> page) {
        return goodsMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page,wrapper);
    }
}
