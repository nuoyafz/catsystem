package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 猫咪-标签关联实体类
 * @author 方舟
 */
@Data
@TableName("t_cat_tag_relation")
public class CatTagRelation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long catId;
    private Long tagId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
