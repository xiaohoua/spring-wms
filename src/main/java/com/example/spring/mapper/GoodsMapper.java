package com.example.spring.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.bcel.classfile.Constant;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-04-27
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageC(IPage<Goods> page);

    IPage pageCC(Page<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
