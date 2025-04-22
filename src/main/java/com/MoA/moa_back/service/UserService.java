package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;
import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;

public interface UserService {
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId);
}
