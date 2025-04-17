package com.MoA.moa_back.service;

import com.MoA.moa_back.common.dto.response.home.HomeSummaryResponseDto;
import org.springframework.http.ResponseEntity;

public interface HomeService {

    ResponseEntity<HomeSummaryResponseDto> getHomeSummary();
}