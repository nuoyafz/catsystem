package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.User;
import com.catfeeding.mapper.UserMapper;
import com.catfeeding.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 * @author 方舟
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
