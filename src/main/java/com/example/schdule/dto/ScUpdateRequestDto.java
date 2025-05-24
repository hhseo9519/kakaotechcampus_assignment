package com.example.schdule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;


@Getter
public class ScUpdateRequestDto {
    private String task;
    private String author;
    private String password;  // 비번 확인용
}

