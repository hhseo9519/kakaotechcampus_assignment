package com.example.schdule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScCreateRequestDto {
    private String task;
    private String author;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
