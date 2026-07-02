package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.CatTag;
import com.catfeeding.entity.CatTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CatTagRelationMapper extends BaseMapper<CatTagRelation> {
    
    @Select("SELECT t.* FROM t_cat_tag t INNER JOIN t_cat_tag_relation r ON t.id = r.tag_id WHERE r.cat_id = #{catId}")
    List<CatTag> selectTagsByCatId(@Param("catId") Long catId);
}
