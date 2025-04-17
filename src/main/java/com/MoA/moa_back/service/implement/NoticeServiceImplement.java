package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.dto.request.notice.*;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.notice.*;
import com.MoA.moa_back.common.entity.NotificationEntity;
import com.MoA.moa_back.repository.NotificationRepository;
import com.MoA.moa_back.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImplement implements NoticeService {

    private final NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<ResponseDto> postNotice(PostNoticeRequestDto dto) {
        try {
            NotificationEntity entity = NotificationEntity.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .build();
            notificationRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseDto> patchNotice(int id, PatchNoticeRequestDto dto) {
        try {
            Optional<NotificationEntity> optional = notificationRepository.findById(id);
            if (optional.isEmpty()) return ResponseDto.noExistBoard();

            NotificationEntity entity = optional.get();
            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            notificationRepository.save(entity);

            return ResponseDto.success(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deleteNotice(int id) {
        try {
            if (!notificationRepository.existsById(id)) return ResponseDto.noExistBoard();
            notificationRepository.deleteById(id);
            return ResponseDto.success(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<?> getNotice(int id) {
        try {
            Optional<NotificationEntity> optional = notificationRepository.findById(id);
            if (optional.isEmpty()) return ResponseDto.noExistBoard();

            NotificationEntity entity = optional.get();
            entity.setViews(entity.getViews() + 1);
            notificationRepository.save(entity);

            return ResponseEntity.status(HttpStatus.OK).body(new GetNoticeResponseDto(entity));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<?> getNoticeList() {
        try {
            List<NotificationEntity> entityList = notificationRepository.findAll();
            List<GetNoticeResponseDto> result = entityList.stream().map(GetNoticeResponseDto::new).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new GetNoticeListResponseDto(result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
}