package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.Adoption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdoptionMapper extends BaseMapper<Adoption> {
    
    @Select("SELECT a.*, c.name as cat_name, c.photo_url as cat_photo, u.username as reviewer_name " +
            "FROM t_adoption a " +
            "LEFT JOIN t_cat c ON a.cat_id = c.id " +
            "LEFT JOIN t_user u ON a.reviewer_id = u.id " +
            "ORDER BY a.create_time DESC")
    List<Adoption> selectAllWithDetails();
}
