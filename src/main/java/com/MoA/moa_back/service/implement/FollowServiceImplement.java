package com.MoA.moa_back.service.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowResponseDto;
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

      if(user.getUserId().equals(followerUserId)){
        return ResponseDto.validationFail();
      }
      
      // FollowEntity 
      FollowEntity followEntity = followRepository.findByFollowerAndFollowee(followerUserId, user.getUserId());
      if(followEntity == null){
        followEntity = new FollowEntity(followerUserId, user.getUserId());
        followRepository.save(followEntity);
      }else{
        followRepository.delete(followEntity);
      }
    }catch(Exception e){
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<? super GetFollowResponseDto> getFollow(String userPageNickname) {
    try{
      UserEntity user = userRepository.findByUserNickname(userPageNickname);
      if(user == null) return ResponseDto.noExistUser();
      List<FollowEntity> followers = followRepository.findByFollower(user.getUserId()); 
      List<FollowEntity> followees = followRepository.findByFollowee(user.getUserId());
      
      return GetFollowResponseDto.success(followers, followees);
    }catch(Exception e){
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
  
}
