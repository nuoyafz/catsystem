package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 流浪猫实体类
 * @author 邢锦涛
 */
@Data
@TableName("t_cat")
public class Cat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String breed;
    private String color;
    private String gender;
    private Integer age;
    private String healthStatus;
    private String photoUrl;
    private String location;
    private String description;
    private Integer sterilized;
    private String adoptionStatus;
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private java.util.List<CatTag> tags;
}
