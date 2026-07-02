package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.Cat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流浪猫Mapper接口
 * @author 邢锦涛
 */
@Mapper
public interface CatMapper extends BaseMapper<Cat> {
}
