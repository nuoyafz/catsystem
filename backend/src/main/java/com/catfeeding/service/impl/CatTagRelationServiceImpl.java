package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.CatTag;
import com.catfeeding.entity.CatTagRelation;
import com.catfeeding.mapper.CatTagRelationMapper;
import com.catfeeding.service.CatTagRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatTagRelationServiceImpl extends ServiceImpl<CatTagRelationMapper, CatTagRelation> implements CatTagRelationService {
    
    @Override
    public List<CatTag> getTagsByCatId(Long catId) {
        return baseMapper.selectTagsByCatId(catId);
    }
    
    @Override
    @Transactional
    public void saveCatTags(Long catId, List<Long> tagIds) {
        // 删除原有标签
        LambdaQueryWrapper<CatTagRelation> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(CatTagRelation::getCatId, catId);
        remove(deleteWrapper);
        
        // 添加新标签
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long tagId : tagIds) {
                CatTagRelation relation = new CatTagRelation();
                relation.setCatId(catId);
                relation.setTagId(tagId);
                save(relation);
            }
        }
    }
}
