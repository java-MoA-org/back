package com.MoA.moa_back.common.dto.response.alert;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.vo.AlertVo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAlertResponseDto extends ResponseDto{
    private List<AlertVo> alerts;

    public static ResponseEntity<GetAlertResponseDto> success(List<AlertVo> alerts) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new GetAlertResponseDto(alerts));
    }
}
