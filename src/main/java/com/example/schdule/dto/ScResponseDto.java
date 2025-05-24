package com.example.schdule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter//get필드 함수들을 생성해준다.
@AllArgsConstructor// 모든 필드값을 파라미터로 갖는 생성자 만들기
public class ScResponseDto {
    long id;
    private String task;
    private String author;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
