package com.learning.platform.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class ParentScheduleDao {

    private final JdbcTemplate jdbcTemplate;

    public ParentScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertSchedule(Long parentId, Long sessionId, Instant startTime, Instant endTime) {
        String sql = "INSERT INTO parent_schedule (parent_id, session_id, schedule_range) " +
                     "VALUES (?, ?, tstzrange(?, ?, '[]'))";
        
        jdbcTemplate.update(sql, 
                parentId, 
                sessionId, 
                Timestamp.from(startTime), 
                Timestamp.from(endTime)
        );
    }

    public void deleteByOfferingId(Long offeringId) {
        String sql = "DELETE FROM parent_schedule WHERE session_id IN " +
                     "(SELECT id FROM session WHERE offering_id = ?)";
        jdbcTemplate.update(sql, offeringId);
    }
}
