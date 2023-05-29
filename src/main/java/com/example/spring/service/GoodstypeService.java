package com.example.spring.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.spring.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-05-22
 */
public interface GoodstypeService extends IService<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);

}
