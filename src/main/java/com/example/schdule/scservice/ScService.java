package com.example.schdule.scservice;

import com.example.schdule.dto.ScCreateRequestDto;
import com.example.schdule.dto.ScResponseDto;
import com.example.schdule.dto.ScUpdateRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScService {
    //1.일정 생성
    ScResponseDto saveTask(ScCreateRequestDto scCreateRequestDto);

    //2.전체일정 조회
    List<ScResponseDto> findAllTasks(String author, LocalDate updatedDate);


    //3. 선택 일정 조회
    ScResponseDto findTaskById(Long id);

    //4. 일정 수정
    ScResponseDto updateTask(Long id, ScUpdateRequestDto dto);
    //5.
    boolean deleteTask(Long id, String password);
}
