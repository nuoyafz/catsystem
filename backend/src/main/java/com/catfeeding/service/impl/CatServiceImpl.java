package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.Cat;
import com.catfeeding.mapper.CatMapper;
import com.catfeeding.service.CatService;
import org.springframework.stereotype.Service;

/**
 * 流浪猫Service实现类
 * @author 邢锦涛
 */
@Service
public class CatServiceImpl extends ServiceImpl<CatMapper, Cat> implements CatService {
}
