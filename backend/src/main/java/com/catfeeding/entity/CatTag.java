package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 猫咪标签实体类
 * @author 方舟
 */
@Data
@TableName("t_cat_tag")
public class CatTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String color;
    private String icon;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
