package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 投喂记录实体类
 * @author 邢锦涛
 */
@Data
@TableName("t_feed_record")
public class FeedRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long catId;
    private LocalDateTime feedTime;
    private String foodType;
    private String amount;
    private String location;
    private String note;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 关联查询字段（非数据库字段） */
    @TableField(exist = false)
    private String catName;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String realName;
}
