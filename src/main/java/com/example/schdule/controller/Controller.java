package com.example.schdule.controller;

import com.example.schdule.dto.ScCreateRequestDto;
import com.example.schdule.dto.ScDeleteRequestDto;
import com.example.schdule.dto.ScResponseDto;
import com.example.schdule.dto.ScUpdateRequestDto;
import com.example.schdule.scservice.ScService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/Schedule")
public class Controller {

    private final ScService scService;
    //생성자 주입,, 이제 scService 객체를 사용가능
    public Controller(ScService scService) {
        this.scService = scService;
    }

    @PostMapping
    //1. 일정 생성 (통과)
    public ResponseEntity<ScResponseDto> createTask(@RequestBody ScCreateRequestDto scCreateRequestDto) {
        //파라미터를 requestbody로 받고 객체 그대로 집어 넣어준거다.
        return new ResponseEntity<>(scService.saveTask(scCreateRequestDto), HttpStatus.CREATED);
    }

    //2. 전체 일정 조회
    @GetMapping
    public List<ScResponseDto> findAllTasks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedDate
    ) {
        return scService.findAllTasks(author, updatedDate);
    }
    //3. 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScResponseDto> findTaskById(@PathVariable Long id) {
        ScResponseDto dto = scService.findTaskById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //4. 수정작업 id로 수정하고 비번 검사하고 있는 id인지도 검사해야힘
    @PatchMapping("/{id}")
    public ResponseEntity<ScResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody ScUpdateRequestDto dto
    ) {
        ScResponseDto updated = scService.updateTask(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);//id가 있고 비번까지 다 있는경우에는 updated에 객체가 들어가게 하자
        } else {
            // id가 없거나 비밀번호 불일치 등으로 수정 못한 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    //5.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestBody @Valid ScDeleteRequestDto dto
    ) {
        boolean deleted = scService.deleteTask(id, dto.getPassword());

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }






}
