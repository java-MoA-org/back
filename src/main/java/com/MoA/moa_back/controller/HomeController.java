package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.response.home.HomeSummaryResponseDto;
import com.MoA.moa_back.service.HomeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/summary")
    public ResponseEntity<HomeSummaryResponseDto> getHomeSummary() {
        return homeService.getHomeSummary();
    }
} 
