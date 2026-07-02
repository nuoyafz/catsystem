package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.Activity;
import com.catfeeding.mapper.ActivityMapper;
import com.catfeeding.service.ActivityService;
import org.springframework.stereotype.Service;

/**
 * 活动Service实现类
 * @author 崔嘉兵
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
