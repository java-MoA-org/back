package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.dto.response.user.SearchUserItem;
import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.vo.UserInfoVO;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    // 닉네임 기반 유저 검색
    @Override
    public SearchUserResponseDto searchUser(String keyword) {
        List<UserEntity> users = userRepository.findByUserNicknameContaining(keyword);

        List<SearchUserItem> userList = users.stream()
                .map(user -> new SearchUserItem(
                        user.getUserId(),
                        user.getUserNickname(),
                        user.getUserProfileImage()
                ))
                .collect(Collectors.toList());

        return new SearchUserResponseDto(true, "검색 성공", userList);
    }

    // userId 기반 유저 정보 조회
    @Override
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfoById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserInfoVO userInfoVO = new UserInfoVO(userEntity, null); // 관심사 미포함
        return GetUserInfoResponseDto.success(userInfoVO);
    }

    // userId 기반 프로필 이미지 경로 반환
    @Override
    public String getUserProfileImagePath(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null || user.getUserProfileImage() == null) return null;
        return user.getUserProfileImage();
    }

    // userId 기반 닉네임 반환
    @Override
    public String getUserNickname(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) return null;
        return user.getUserNickname();
    }
}