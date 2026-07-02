package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * @author 方舟
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
