package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.Adoption;
import com.catfeeding.mapper.AdoptionMapper;
import com.catfeeding.service.AdoptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionServiceImpl extends ServiceImpl<AdoptionMapper, Adoption> implements AdoptionService {
    
    @Override
    public List<Adoption> getAllWithDetails() {
        return baseMapper.selectAllWithDetails();
    }
}
