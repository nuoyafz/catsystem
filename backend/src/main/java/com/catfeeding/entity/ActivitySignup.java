package com.catfeeding.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 活动报名实体类
 * @author 崔嘉兵
 */
@Data
@TableName("t_activity_signup")
public class ActivitySignup {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long activityId;
    private Long userId;
    private LocalDateTime signupTime;
    private BigDecimal hours;
}
