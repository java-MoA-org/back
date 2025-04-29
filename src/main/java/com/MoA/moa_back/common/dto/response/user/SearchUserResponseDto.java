package com.MoA.moa_back.common.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResponseDto {
    private boolean success;
    private String message;
    private List<SearchUserItem> userList;
}