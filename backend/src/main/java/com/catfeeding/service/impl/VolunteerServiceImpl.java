package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.Volunteer;
import com.catfeeding.mapper.VolunteerMapper;
import com.catfeeding.service.VolunteerService;
import org.springframework.stereotype.Service;

/**
 * 志愿者Service实现类
 * @author 崔嘉兵
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {
}
