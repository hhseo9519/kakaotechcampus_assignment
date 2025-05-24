package com.example.schdule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {


    private Long id;
    @Setter
    private String task;
    @Setter
    private String author;
    @Setter
    private String password;
    @Setter
    private LocalDateTime created_at;
    @Setter
    private LocalDateTime updated_at;

    //생성자 만들기
    public Schedule(String task, String author, String password, LocalDateTime created_at, LocalDateTime updated_at) {
        this.task = task;
        this.author = author;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }



}
