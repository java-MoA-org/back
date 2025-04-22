package com.MoA.moa_back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.vo.UserInfoVO;
import com.MoA.moa_back.repository.UserInterestsRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{

    private final UserRepository userRepository;
    private final UserInterestsRepository userInterestsRepository;

    @Override
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId) {
        
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserInterestsEntity userInterestsEntity = userInterestsRepository.findByUserId(userId);
        
        UserInfoVO userInfoVO = new UserInfoVO(userEntity, userInterestsEntity);

        return GetUserInfoResponseDto.success(userInfoVO);
    }
    
}
