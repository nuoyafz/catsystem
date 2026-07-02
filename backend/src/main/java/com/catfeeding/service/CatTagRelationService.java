package com.catfeeding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.catfeeding.entity.CatTag;
import com.catfeeding.entity.CatTagRelation;

import java.util.List;

public interface CatTagRelationService extends IService<CatTagRelation> {
    List<CatTag> getTagsByCatId(Long catId);
    void saveCatTags(Long catId, List<Long> tagIds);
}
