package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.dto.response.user.SearchUserItem;
import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public SearchUserResponseDto searchUser(String keyword) {
        List<UserEntity> users = userRepository.findByUserNicknameContaining(keyword);

        List<SearchUserItem> userList = users.stream()
                .map(user -> new SearchUserItem(
                    user.getUserNickname(),
                    user.getUserProfileImage() 
                ))
                .collect(Collectors.toList());

        return new SearchUserResponseDto(true, "검색 성공", userList);
    }
}