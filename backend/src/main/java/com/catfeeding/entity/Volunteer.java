package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 志愿者实体类
 * @author 崔嘉兵
 */
@Data
@TableName("t_volunteer")
public class Volunteer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String realName;
    private String studentId;
    private String department;
    private String phone;
    private BigDecimal serviceHours;
    private LocalDateTime joinTime;

    @TableField(exist = false)
    private String username;
}
