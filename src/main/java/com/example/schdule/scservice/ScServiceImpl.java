package com.example.schdule.scservice;

import com.example.schdule.dto.ScCreateRequestDto;
import com.example.schdule.dto.ScResponseDto;
import com.example.schdule.dto.ScUpdateRequestDto;
import com.example.schdule.entity.Schedule;
import com.example.schdule.repository.ScRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScServiceImpl implements ScService {
    //이제 scRepository 인터페이스를 넣어서 함수를 호출하는 식으로 하자
private final ScRepository scRepository;

    public ScServiceImpl(ScRepository scRepository) {
        this.scRepository = scRepository;
    }

    @Override
    @Transactional
    public ScResponseDto saveTask(ScCreateRequestDto scCreateRequestDto) {
        // 1. 요청 DTO를 사용해 Schedule 엔티티 생성 (builder 없이 생성자 사용)
        Schedule schedule = new Schedule();
        schedule.setTask(scCreateRequestDto.getTask()); // 할 일 내용
        schedule.setAuthor(scCreateRequestDto.getAuthor()); // 작성자 이름
        schedule.setPassword(scCreateRequestDto.getPassword()); // 비밀번호
        schedule.setCreated_at(null); //알아서 추가 되겠지
        schedule.setUpdated_at(null);



        // 2. Schedule 엔티티를 데이터베이스에 저장
        Schedule savedSchedule = scRepository.saveTask(schedule);

        // 3. 저장된 Schedule 엔티티를 응답 DTO로 변환하여 반환
        ScResponseDto responseDto = new ScResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTask(),
                savedSchedule.getAuthor(),
                savedSchedule.getCreated_at(),
                savedSchedule.getUpdated_at()
        );

        return responseDto;
    }

    @Override
    public List<ScResponseDto> findAllTasks(String author, LocalDate updatedDate) {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (updatedDate != null) {
            startDateTime = updatedDate.atStartOfDay();
            endDateTime = updatedDate.atTime(LocalTime.MAX);
        }

        return scRepository.findAllTasks(author, startDateTime, endDateTime);
    }

    @Override
    public ScResponseDto findTaskById(Long id) {
        // Schedule 엔티티를 찾아서 DTO로 변환해서 반환
        Schedule schedule = scRepository.findById(id);
        if (schedule == null) {
            return null;
        }
        return new ScResponseDto(
                schedule.getId(),
                schedule.getTask(),
                schedule.getAuthor(),
                schedule.getCreated_at(),
                schedule.getUpdated_at()
        );
    }
    //4.
    @Override
    @Transactional
    public ScResponseDto updateTask(Long id, ScUpdateRequestDto dto) {
        // db에서 직접 찾아보자 id도 맞고 password도 맞는게 있으면 나오겠지
        int rows = scRepository.updateTask(id, dto.getTask(), dto.getAuthor(), dto.getPassword());
        if (rows == 0) {
            return null;
        }
        // 2) 업데이트된 레코드 재조회
        Schedule s = scRepository.findById(id);
        if (s == null) {
            return null;
        }
        return new ScResponseDto(
                s.getId(),
                s.getTask(),
                s.getAuthor(),
                s.getCreated_at(),
                s.getUpdated_at()
        );
    }

    @Override
    @Transactional
    public boolean deleteTask(Long id, String password) {
        int rows = scRepository.deleteTask(id, password);
        return rows > 0;
    }


}
