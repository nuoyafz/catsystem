package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.CatTag;
import com.catfeeding.mapper.CatTagMapper;
import com.catfeeding.service.CatTagService;
import org.springframework.stereotype.Service;

@Service
public class CatTagServiceImpl extends ServiceImpl<CatTagMapper, CatTag> implements CatTagService {
}
