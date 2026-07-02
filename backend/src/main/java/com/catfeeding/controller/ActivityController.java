package com.catfeeding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catfeeding.common.Result;
import com.catfeeding.entity.Activity;
import com.catfeeding.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动控制器
 * @author 崔嘉兵
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 获取所有活动
     */
    @GetMapping("/list")
    public Result<List<Activity>> getAllActivities(@RequestParam(required = false) String status) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(status)) {
            wrapper.eq(Activity::getStatus, status);
        }
        wrapper.orderByDesc(Activity::getStartTime);
        return Result.success(activityService.list(wrapper));
    }

    /**
     * 根据ID获取活动
     */
    @GetMapping("/{id}")
    public Result<Activity> getById(@PathVariable Long id) {
        return Result.success(activityService.getById(id));
    }

    /**
     * 添加活动
     */
    @PostMapping
    public Result<Void> addActivity(@RequestBody Activity activity) {
        activityService.save(activity);
        return Result.success();
    }

    /**
     * 更新活动
     */
    @PutMapping
    public Result<Void> updateActivity(@RequestBody Activity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    /**
     * 删除活动
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteActivity(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }
}
