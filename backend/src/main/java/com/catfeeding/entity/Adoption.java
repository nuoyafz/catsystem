package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 领养申请实体类
 * @author 方舟
 */
@Data
@TableName("t_adoption")
public class Adoption {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long catId;
    private String applicantName;
    private String applicantPhone;
    private String applicantEmail;
    private String applicantAddress;
    private String reason;
    private String experience;
    private String status;
    private Long reviewerId;
    private String reviewComment;
    private LocalDateTime reviewTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String catName;
    @TableField(exist = false)
    private String catPhoto;
    @TableField(exist = false)
    private String reviewerName;
}
