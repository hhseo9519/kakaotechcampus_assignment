package com.example.schdule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScDeleteRequestDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}