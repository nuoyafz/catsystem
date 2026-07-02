package com.catfeeding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.catfeeding.common.Result;
import com.catfeeding.entity.Cat;
import com.catfeeding.entity.CatTag;
import com.catfeeding.service.CatService;
import com.catfeeding.service.CatTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流浪猫控制器
 * @author 邢锦涛
 */
@RestController
@RequestMapping("/api/cat")
public class CatController {

    @Autowired
    private CatService catService;
    
    @Autowired
    private CatTagRelationService catTagRelationService;

    /**
     * 分页查询流浪猫
     */
    @GetMapping("/page")
    public Result<Page<Cat>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String healthStatus) {

        Page<Cat> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Cat> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(name)) {
            wrapper.like(Cat::getName, name);
        }
        if (StringUtils.hasText(healthStatus)) {
            wrapper.eq(Cat::getHealthStatus, healthStatus);
        }
        wrapper.orderByDesc(Cat::getCreateTime);

        return Result.success(catService.page(page, wrapper));
    }

    /**
     * 获取所有流浪猫（包含标签）
     */
    @GetMapping("/list")
    public Result<java.util.List<Cat>> getAllCats() {
        LambdaQueryWrapper<Cat> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Cat::getCreateTime);
        List<Cat> cats = catService.list(wrapper);
        
        // 为每只猫加载标签
        for (Cat cat : cats) {
            List<CatTag> tags = catTagRelationService.getTagsByCatId(cat.getId());
            cat.setTags(tags);
        }
        
        return Result.success(cats);
    }

    /**
     * 根据ID获取流浪猫
     */
    @GetMapping("/{id}")
    public Result<Cat> getById(@PathVariable Long id) {
        return Result.success(catService.getById(id));
    }

    /**
     * 添加流浪猫（包含标签）
     */
    @PostMapping
    public Result<Void> addCat(@RequestBody Cat cat) {
        catService.save(cat);
        if (cat.getTags() != null && !cat.getTags().isEmpty()) {
            List<Long> tagIds = cat.getTags().stream()
                .map(CatTag::getId)
                .collect(java.util.stream.Collectors.toList());
            catTagRelationService.saveCatTags(cat.getId(), tagIds);
        }
        return Result.success();
    }

    /**
     * 更新流浪猫（包含标签）
     */
    @PutMapping
    public Result<Void> updateCat(@RequestBody Cat cat) {
        catService.updateById(cat);
        if (cat.getTags() != null) {
            List<Long> tagIds = cat.getTags().stream()
                .map(CatTag::getId)
                .collect(java.util.stream.Collectors.toList());
            catTagRelationService.saveCatTags(cat.getId(), tagIds);
        }
        return Result.success();
    }

    /**
     * 删除流浪猫
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCat(@PathVariable Long id) {
        catService.removeById(id);
        return Result.success();
    }
}
