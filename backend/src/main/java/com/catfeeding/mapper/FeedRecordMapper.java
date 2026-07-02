package com.catfeeding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.catfeeding.entity.FeedRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 投喂记录Mapper接口
 * @author 邢锦涛
 */
@Mapper
public interface FeedRecordMapper extends BaseMapper<FeedRecord> {
    @Select("SELECT r.*, c.name as cat_name, u.username, COALESCE(v.real_name, u.username) as real_name FROM t_feed_record r " +
            "LEFT JOIN t_cat c ON r.cat_id = c.id " +
            "LEFT JOIN t_user u ON r.user_id = u.id " +
            "LEFT JOIN t_volunteer v ON r.user_id = v.user_id " +
            "ORDER BY r.feed_time DESC")
    List<FeedRecord> selectAllWithDetails();
}
