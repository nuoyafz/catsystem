package com.catfeeding.controller;

import com.catfeeding.common.Result;
import com.catfeeding.entity.Adoption;
import com.catfeeding.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption")
public class AdoptionController {
    
    @Autowired
    private AdoptionService adoptionService;
    
    @GetMapping("/list")
    public Result<List<Adoption>> getAllAdoptions() {
        return Result.success(adoptionService.getAllWithDetails());
    }
    
    @GetMapping("/{id}")
    public Result<Adoption> getAdoptionById(@PathVariable Long id) {
        return Result.success(adoptionService.getById(id));
    }
    
    @PostMapping
    public Result<String> applyAdoption(@RequestBody Adoption adoption) {
        adoptionService.save(adoption);
        return Result.success();
    }
    
    @PutMapping
    public Result<String> updateAdoption(@RequestBody Adoption adoption) {
        adoptionService.updateById(adoption);
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteAdoption(@PathVariable Long id) {
        adoptionService.removeById(id);
        return Result.success();
    }
    
    @PutMapping("/review/{id}")
    public Result<String> reviewAdoption(@PathVariable Long id, @RequestBody Adoption adoption) {
        Adoption existing = adoptionService.getById(id);
        if (existing == null) {
            return Result.error("领养申请不存在");
        }
        existing.setStatus(adoption.getStatus());
        existing.setReviewerId(adoption.getReviewerId());
        existing.setReviewComment(adoption.getReviewComment());
        existing.setReviewTime(java.time.LocalDateTime.now());
        adoptionService.updateById(existing);
        return Result.success();
    }
}
