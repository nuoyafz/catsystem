package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.Volunteer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 志愿者Mapper接口
 * @author 崔嘉兵
 */
@Mapper
public interface VolunteerMapper extends BaseMapper<Volunteer> {
    @Select("SELECT v.*, u.username FROM t_volunteer v " +
            "LEFT JOIN t_user u ON v.user_id = u.id " +
            "ORDER BY v.service_hours DESC")
    List<Volunteer> selectAllWithUsername();
}
