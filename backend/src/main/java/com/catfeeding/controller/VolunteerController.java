package com.catfeeding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catfeeding.common.Result;
import com.catfeeding.entity.Volunteer;
import com.catfeeding.mapper.VolunteerMapper;
import com.catfeeding.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 志愿者控制器
 * @author 崔嘉兵
 */
@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerMapper volunteerMapper;

    /**
     * 获取所有志愿者（带用户名）
     */
    @GetMapping("/list")
    public Result<List<Volunteer>> getAllVolunteers() {
        return Result.success(volunteerMapper.selectAllWithUsername());
    }

    /**
     * 根据ID获取志愿者
     */
    @GetMapping("/{id}")
    public Result<Volunteer> getById(@PathVariable Long id) {
        return Result.success(volunteerService.getById(id));
    }

    /**
     * 添加志愿者
     */
    @PostMapping
    public Result<Void> addVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.save(volunteer);
        return Result.success();
    }

    /**
     * 更新志愿者
     */
    @PutMapping
    public Result<Void> updateVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.updateById(volunteer);
        return Result.success();
    }

    /**
     * 删除志愿者
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.removeById(id);
        return Result.success();
    }

    /**
     * 根据用户ID获取志愿者信息
     */
    @GetMapping("/user/{userId}")
    public Result<Volunteer> getByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<Volunteer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Volunteer::getUserId, userId);
        return Result.success(volunteerService.getOne(wrapper));
    }
}
