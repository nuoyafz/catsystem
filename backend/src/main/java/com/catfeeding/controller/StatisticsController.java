package com.catfeeding.controller;

import com.catfeeding.common.Result;
import com.catfeeding.entity.Cat;
import com.catfeeding.entity.FeedRecord;
import com.catfeeding.entity.Volunteer;
import com.catfeeding.service.CatService;
import com.catfeeding.service.FeedRecordService;
import com.catfeeding.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    
    @Autowired
    private CatService catService;
    
    @Autowired
    private FeedRecordService feedRecordService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @GetMapping("/feed-trend")
    public Result<List<Map<String, Object>>> getFeedTrend(@RequestParam(defaultValue = "7") Integer days) {
        List<FeedRecord> allRecords = feedRecordService.list();
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        
        Map<String, Integer> trendMap = new LinkedHashMap<>();
        for (int i = days - 1; i >= 0; i--) {
            String date = LocalDateTime.now().minusDays(i).toLocalDate().toString();
            trendMap.put(date, 0);
        }
        
        for (FeedRecord record : allRecords) {
            if (record.getFeedTime().isAfter(startDate)) {
                String date = record.getFeedTime().toLocalDate().toString();
                trendMap.put(date, trendMap.getOrDefault(date, 0) + 1);
            }
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trendMap.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("date", entry.getKey());
            item.put("count", entry.getValue());
            result.add(item);
        }
        
        return Result.success(result);
    }
    
    @GetMapping("/cat-ranking")
    public Result<List<Map<String, Object>>> getCatRanking(@RequestParam(defaultValue = "5") Integer limit) {
        List<FeedRecord> allRecords = feedRecordService.list();
        
        Map<Long, Integer> catCountMap = new HashMap<>();
        for (FeedRecord record : allRecords) {
            catCountMap.put(record.getCatId(), catCountMap.getOrDefault(record.getCatId(), 0) + 1);
        }
        
        List<Map<String, Object>> result = catCountMap.entrySet().stream()
            .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
            .limit(limit)
            .map(entry -> {
                Map<String, Object> item = new HashMap<>();
                Cat cat = catService.getById(entry.getKey());
                item.put("catId", entry.getKey());
                item.put("catName", cat != null ? cat.getName() : "未知");
                item.put("count", entry.getValue());
                return item;
            })
            .collect(Collectors.toList());
        
        return Result.success(result);
    }
    
    @GetMapping("/volunteer-ranking")
    public Result<List<Map<String, Object>>> getVolunteerRanking(@RequestParam(defaultValue = "5") Integer limit) {
        List<Volunteer> volunteers = volunteerService.list();
        
        List<Map<String, Object>> result = volunteers.stream()
            .sorted((v1, v2) -> v2.getServiceHours().compareTo(v1.getServiceHours()))
            .limit(limit)
            .map(v -> {
                Map<String, Object> item = new HashMap<>();
                item.put("volunteerId", v.getId());
                item.put("name", v.getRealName());
                item.put("serviceHours", v.getServiceHours());
                return item;
            })
            .collect(Collectors.toList());
        
        return Result.success(result);
    }
    
    @GetMapping("/location-distribution")
    public Result<List<Map<String, Object>>> getLocationDistribution() {
        List<Cat> cats = catService.list();
        
        Map<String, Integer> locationMap = new HashMap<>();
        for (Cat cat : cats) {
            if (cat.getLocation() != null && !cat.getLocation().isEmpty()) {
                locationMap.put(cat.getLocation(), locationMap.getOrDefault(cat.getLocation(), 0) + 1);
            }
        }
        
        List<Map<String, Object>> result = locationMap.entrySet().stream()
            .map(entry -> {
                Map<String, Object> item = new HashMap<>();
                item.put("location", entry.getKey());
                item.put("count", entry.getValue());
                return item;
            })
            .collect(Collectors.toList());
        
        return Result.success(result);
    }
    
    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        long totalCats = catService.count();
        long totalFeedRecords = feedRecordService.count();
        long totalVolunteers = volunteerService.count();
        
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        long todayFeedCount = feedRecordService.list().stream()
            .filter(r -> r.getFeedTime().isAfter(todayStart))
            .count();
        
        overview.put("totalCats", totalCats);
        overview.put("totalFeedRecords", totalFeedRecords);
        overview.put("totalVolunteers", totalVolunteers);
        overview.put("todayFeedCount", todayFeedCount);
        
        return Result.success(overview);
    }
}
