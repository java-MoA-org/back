package com.MoA.moa_back.service;

import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;

public interface UserService {
    SearchUserResponseDto searchUser(String keyword);
}