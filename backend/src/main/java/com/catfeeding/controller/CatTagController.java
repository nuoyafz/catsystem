package com.catfeeding.controller;

import com.catfeeding.common.Result;
import com.catfeeding.entity.CatTag;
import com.catfeeding.service.CatTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat-tag")
public class CatTagController {
    
    @Autowired
    private CatTagService catTagService;
    
    @GetMapping("/list")
    public Result<List<CatTag>> getAllTags() {
        return Result.success(catTagService.list());
    }
    
    @GetMapping("/{id}")
    public Result<CatTag> getTagById(@PathVariable Long id) {
        return Result.success(catTagService.getById(id));
    }
    
    @PostMapping
    public Result<String> addTag(@RequestBody CatTag tag) {
        catTagService.save(tag);
        return Result.success();
    }
    
    @PutMapping
    public Result<String> updateTag(@RequestBody CatTag tag) {
        catTagService.updateById(tag);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteTag(@PathVariable Long id) {
        catTagService.removeById(id);
        return Result.success();
    }
}
