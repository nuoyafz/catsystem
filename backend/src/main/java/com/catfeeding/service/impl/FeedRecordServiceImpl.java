package com.catfeeding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catfeeding.entity.FeedRecord;
import com.catfeeding.mapper.FeedRecordMapper;
import com.catfeeding.service.FeedRecordService;
import org.springframework.stereotype.Service;

/**
 * 投喂记录Service实现类
 * @author 邢锦涛
 */
@Service
public class FeedRecordServiceImpl extends ServiceImpl<FeedRecordMapper, FeedRecord> implements FeedRecordService {
}
