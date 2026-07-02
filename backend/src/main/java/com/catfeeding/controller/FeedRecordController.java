package com.catfeeding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.catfeeding.common.Result;
import com.catfeeding.entity.FeedRecord;
import com.catfeeding.mapper.FeedRecordMapper;
import com.catfeeding.service.FeedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投喂记录控制器
 * @author 邢锦涛
 */
@RestController
@RequestMapping("/api/feed")
public class FeedRecordController {

    @Autowired
    private FeedRecordService feedRecordService;

    @Autowired
    private FeedRecordMapper feedRecordMapper;

    /**
     * 分页查询投喂记录（带关联信息）
     */
    @GetMapping("/page")
    public Result<Page<FeedRecord>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long catId,
            @RequestParam(required = false) Long userId) {

        Page<FeedRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<FeedRecord> wrapper = new LambdaQueryWrapper<>();

        if (catId != null) {
            wrapper.eq(FeedRecord::getCatId, catId);
        }
        if (userId != null) {
            wrapper.eq(FeedRecord::getUserId, userId);
        }
        wrapper.orderByDesc(FeedRecord::getFeedTime);

        return Result.success(feedRecordService.page(page, wrapper));
    }

    /**
     * 获取所有投喂记录（带关联信息）
     */
    @GetMapping("/list")
    public Result<List<FeedRecord>> getAllRecords() {
        return Result.success(feedRecordMapper.selectAllWithDetails());
    }

    /**
     * 根据ID获取投喂记录
     */
    @GetMapping("/{id}")
    public Result<FeedRecord> getById(@PathVariable Long id) {
        return Result.success(feedRecordService.getById(id));
    }

    /**
     * 添加投喂记录
     */
    @PostMapping
    public Result<Void> addRecord(@RequestBody FeedRecord record) {
        feedRecordService.save(record);
        return Result.success();
    }

    /**
     * 更新投喂记录
     */
    @PutMapping
    public Result<Void> updateRecord(@RequestBody FeedRecord record) {
        feedRecordService.updateById(record);
        return Result.success();
    }

    /**
     * 删除投喂记录
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRecord(@PathVariable Long id) {
        feedRecordService.removeById(id);
        return Result.success();
    }

    /**
     * 统计投喂次数
     */
    @GetMapping("/count")
    public Result<Long> getFeedCount() {
        return Result.success(feedRecordService.count());
    }
}
