package com.catfeeding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.catfeeding.entity.Adoption;

import java.util.List;

public interface AdoptionService extends IService<Adoption> {
    List<Adoption> getAllWithDetails();
}
