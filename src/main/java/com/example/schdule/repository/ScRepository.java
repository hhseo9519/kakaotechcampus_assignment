package com.example.schdule.repository;

import com.example.schdule.dto.ScCreateRequestDto;
import com.example.schdule.dto.ScResponseDto;
import com.example.schdule.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScRepository {
    //1. 메모 생성
    Schedule saveTask(Schedule schedule);

    List<ScResponseDto> findAllTasks(String author, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Schedule findById(Long id);
    //4.
    int updateTask(Long id, String task, String author, String password);

    int deleteTask(Long id, String password);
}
