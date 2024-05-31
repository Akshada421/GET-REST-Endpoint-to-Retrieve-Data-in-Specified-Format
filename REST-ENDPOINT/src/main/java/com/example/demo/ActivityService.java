package com.example.demo;

import java.time.LocalDate;
import java.util.Map;
import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

	@Autowired
    private ActivityRepository activityRepository;

    public Map<String, Object> getActivityStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate last30Days = today.minusDays(30);

        List<ActivityStatisticsForMonth> monthlyStats = activityRepository.findActivityStatisticsForMonth(last30Days);
        List<ActivityStatisticsForDay> yesterdayStats = activityRepository.findActivityStatisticsForDay(yesterday);
        List<ActivityStatisticsForDay> todayStats = activityRepository.findActivityStatisticsForDay(today);

        List<Map<String, Object>> activityStatisticsForMonth = new ArrayList<>();
        for (ActivityStatisticsForMonth stat : monthlyStats) {
            Map<String, Object> activity = new HashMap<>();
            activity.put("activity_name", stat.getName());
            activity.put("occurrences", stat.getOccurrences());
            activityStatisticsForMonth.add(activity);
        }

        Map<String, Integer> yesterdayMap = new HashMap<>();
        for (ActivityStatisticsForDay stat : yesterdayStats) {
            yesterdayMap.put(stat.getName(), stat.getOccurrences());
        }

        Map<String, Integer> todayMap = new HashMap<>();
        for (ActivityStatisticsForDay stat : todayStats) {
            todayMap.put(stat.getName(), stat.getOccurrences());
        }

        Set<String> allActivities = new HashSet<>();
        allActivities.addAll(yesterdayMap.keySet());
        allActivities.addAll(todayMap.keySet());

        List<Map<String, Object>> activityStatisticsYesterdayVsToday = new ArrayList<>();
        for (String activity : allActivities) {
            int yesterdayOccurrences = yesterdayMap.getOrDefault(activity, 0);
            int todayOccurrences = todayMap.getOrDefault(activity, 0);
            String status;
            if (todayOccurrences > yesterdayOccurrences) {
                status = "positive";
            } else if (todayOccurrences < yesterdayOccurrences) {
                status = "negative";
            } else {
                status = "unaltered";
            }

            Map<String, Object> comparison = new HashMap<>();
            comparison.put("activity_name", activity);
            comparison.put("yesterday_occurrences", yesterdayOccurrences);
            comparison.put("today_occurrences", todayOccurrences);
            comparison.put("status", status);
            activityStatisticsYesterdayVsToday.add(comparison);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("activity_statistics_for_month", activityStatisticsForMonth);
        response.put("activity_statistics_yesterday_vs_today", activityStatisticsYesterdayVsToday);

        return response;
    }
}
