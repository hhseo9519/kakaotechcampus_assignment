package com.example.schdule.repository;

import com.example.schdule.dto.ScResponseDto;
import com.example.schdule.entity.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScRepository implements ScRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public JdbcTemplateScRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc    = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Schedule saveTask(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id")
                .usingColumns("task", "author", "password");

        Map<String, Object> params = new HashMap<>();
        params.put("task",     schedule.getTask());
        params.put("author",   schedule.getAuthor());
        params.put("password", schedule.getPassword());

        Number generatedId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        long id = generatedId.longValue();

        // 바로 다시 조회해서 created_at/updated_at 가져오기
        return jdbcTemplate.queryForObject(
                "SELECT id, task, author, password, created_at, updated_at FROM schedule WHERE id = ?",
                (rs, rn) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("author"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ),
                id
        );
    }

    @Override
    public List<ScResponseDto> findAllTasks(String author, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        StringBuilder sql = new StringBuilder("SELECT id, task, author, created_at, updated_at FROM schedule WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (author != null && !author.isBlank()) {
            sql.append(" AND author = :author");
            params.addValue("author", author);
        }
        if (startDateTime != null && endDateTime != null) {
            sql.append(" AND updated_at BETWEEN :startDateTime AND :endDateTime");
            params.addValue("startDateTime", Timestamp.valueOf(startDateTime));
            params.addValue("endDateTime",   Timestamp.valueOf(endDateTime));
        }

        sql.append(" ORDER BY updated_at DESC");

        return namedJdbc.query(sql.toString(), params, rowMapper());
    }

    private RowMapper<ScResponseDto> rowMapper() {
        return (rs, rowNum) -> new ScResponseDto(
                rs.getLong("id"),
                rs.getString("task"),
                rs.getString("author"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    //3번

    @Override
    public Schedule findById(Long id) {
        String sql = "SELECT id, task, author, password, created_at, updated_at " +
                "FROM schedule WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, scheduleRowMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            // 결과가 없을 때는 null 반환
            return null;
        }
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            // Schedule(Long id, String task, String author, String password,
            //          LocalDateTime createdAt, LocalDateTime updatedAt)
            return new Schedule(
                    rs.getLong("id"),
                    rs.getString("task"),
                    rs.getString("author"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
    //4.
    @Override
    public int updateTask(Long id, String task, String author, String password) {
        // 작성일(created_at)은 건드리지 않고,
        // 할일(task), 작성자(author)만 수정, 수정일(updated_at)은 DB CURRENT_TIMESTAMP 자동 처리
        String sql = ""
                + "UPDATE schedule "
                + "SET task = ?, author = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE id = ? AND password = ?";
        return jdbcTemplate.update(sql, task, author, id, password);
    }

    @Override
    public int deleteTask(Long id, String password) {
        // id와 password가 일치하는 경우에만 삭제
        String sql = "DELETE FROM schedule WHERE id = ? AND password = ?";
        return jdbcTemplate.update(sql, id, password);
    }

}
