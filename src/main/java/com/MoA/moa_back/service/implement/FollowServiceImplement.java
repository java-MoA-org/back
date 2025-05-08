package com.MoA.moa_back.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowInfoResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetUserFollowInfoResponseDto;
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
        followEntity = new FollowEntity(user.getUserId(), followerUserId);
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

  @Override
  public ResponseEntity<? super GetFollowInfoResponseDto> getFollowInfo(String loginUserId, String userPageNickname) {
    try{
      UserEntity user = userRepository.findByUserNickname(userPageNickname);
      if(user == null) return ResponseDto.noExistUser();
      List<FollowEntity> followerEntities  = followRepository.findByFollowee(user.getUserId()); 
      List<FollowEntity> followeeEntities  = followRepository.findByFollower(user.getUserId());
  
      List<String> followerIds = followerEntities.stream()
          .map(FollowEntity::getFollower)
          .collect(Collectors.toList());
  
      List<String> followeeIds = followeeEntities.stream()
          .map(FollowEntity::getFollowee)
          .collect(Collectors.toList());
  
          // 4) 한 번에 UserEntity 리스트로 조회 (N+1 방지)
      List<UserEntity> followerUsers = userRepository.findByUserIdIn(followerIds);  // ← UserRepository 쪽으로 이동된 메서드
      List<UserEntity> followeeUsers = userRepository.findByUserIdIn(followeeIds);
  
      List<GetUserFollowInfoResponseDto> followerDtos = new ArrayList<>();
  for (UserEntity u : followerUsers) {
    // boolean isFollowed = followRepository.existsByFollowerAndFollowee(loginUserId, u.getUserId());
      followerDtos.add(new GetUserFollowInfoResponseDto(
          u.getUserId(),
          u.getUserNickname(),
          u.getUserIntroduce(),
          u.getProfileImage(),
          followRepository.existsByFollowerAndFollowee(loginUserId, u.getUserId()) ));
  }
  
  List<GetUserFollowInfoResponseDto> followeeDtos = new ArrayList<>();
  for (UserEntity u : followeeUsers) {
    boolean isFollowed = followRepository.existsByFollowerAndFollowee(loginUserId, u.getUserId());
      followeeDtos.add(new GetUserFollowInfoResponseDto(
          u.getUserId(),
          u.getUserNickname(),
          u.getUserIntroduce(),
          u.getProfileImage(),
          isFollowed ));
  }
      return GetFollowInfoResponseDto.success(followerDtos, followeeDtos);

    }catch(Exception e){
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
}
}
