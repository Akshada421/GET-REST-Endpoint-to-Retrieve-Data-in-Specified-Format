package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {
	@Autowired
    private ActivityService activityService;

    @GetMapping("/activity_statistics")
    public Map<String, Object> getActivityStatistics() {
        return activityService.getActivityStatistics();
    }
}
