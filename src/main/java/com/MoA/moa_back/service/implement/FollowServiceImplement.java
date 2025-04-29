package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.FollowEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.FollowRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.FollowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceImplement implements FollowService{


    private final FollowRepository followRepository;
    private final UserRepository userRepository;

// 팔로우 클릭 시
  @Override
  public ResponseEntity<ResponseDto> postFollow(String followerUserId, String followeeNickname) {
    try{
      UserEntity user = userRepository.findByUserNickname(followeeNickname);

      // pathvariable로 받은 닉네임을 꺼내서 userid를 받아 followeeid를 변경
      // dto.setFolloweeId(user.getUserId());
      // dto.setFollowerId(userId);
      if(user.getUserId().equals(followerUserId)){
        return ResponseDto.validationFail();
      }
      
      FollowEntity followEntity = new FollowEntity(user.getUserId(), followerUserId);
      followRepository.save(followEntity);
    }catch(Exception e){
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }
  
}
