package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("SELECT a.name AS name, SUM(a.occurrences) AS occurrences " +
	           "FROM Activity a " +
	           "WHERE a.occurrenceDate >= :startDate " +
	           "GROUP BY a.name " +
	           "ORDER BY occurrences DESC")
	    List<ActivityStatisticsForMonth> findActivityStatisticsForMonth(LocalDate startDate);

	    @Query("SELECT a.name AS name, SUM(a.occurrences) AS occurrences " +
	           "FROM Activity a " +
	           "WHERE a.occurrenceDate = :date " +
	           "GROUP BY a.name")
	    List<ActivityStatisticsForDay> findActivityStatisticsForDay(LocalDate date);
}
