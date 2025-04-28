package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public SearchUserResponseDto searchUser(@RequestParam("keyword") String keyword) {
        return userService.searchUser(keyword);
    }
}