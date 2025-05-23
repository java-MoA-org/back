package com.MoA.moa_back.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.daily.PatchDailyRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.daily.LikedUserDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyListResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetLikedUserListResponseDto;
import com.MoA.moa_back.common.dto.response.daily.DailySummaryResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyCommentResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyResponseDto;
import com.MoA.moa_back.common.entity.DailyCommentEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.DailyLikeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.util.PageUtil;
import com.MoA.moa_back.common.vo.DailyCommentVO;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.DailyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyServiceImplement implements DailyService {

  private final DailyRepository dailyRepository;
  private final DailyLikeRepository dailyLikeRepository;
  private final DailyCommentRepository dailyCommentRepository;
  private final UserRepository userRepository;

  // method: 일상 게시글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postDailyBoard(PostDailyRequestDto dto, String userId) {
    try {
      DailyEntity dailyEntity = new DailyEntity(dto, userId);
      dailyRepository.save(dailyEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }
  
  // method: 일상 게시판 일상 게시글 목록 조회 //
  @Override
  public ResponseEntity<? super GetDailyListResponseDto> getDailyBoardList(Integer pageNumber, String sortOption) {
    try {
      int pageSize = 10;
      int pageCountPerSection = 5;
  
      Sort sort = resolveSortOption(sortOption);
      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);
  
      Page<DailyEntity> page = dailyRepository.findAll(pageable);
  
      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), page.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }
  
      List<DailySummaryResponseDto> list = page.stream()
        .map(entity -> {
          int likeCount = dailyLikeRepository.countByDailySequence(entity.getDailySequence());
          int commentCount = dailyCommentRepository.countByDailySequence(entity.getDailySequence());
          UserEntity user = userRepository.findByUserId(entity.getUserId());
          String profileImage = (user.getProfileImage() != null) ? user.getProfileImage() : null;
          String nickname = (user != null) ? user.getUserNickname() : null;
  
          return new DailySummaryResponseDto(
            entity.getDailySequence(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreationDate(),
            profileImage,
            nickname,
            entity.getViews(),
            likeCount,
            commentCount,
            entity.getImages()
          );
        })
        .sorted((a, b) -> {
          if ("LIKES".equalsIgnoreCase(sortOption)) {
            return Integer.compare(b.getLikeCount(), a.getLikeCount());
          }
          return 0;
        })
        .toList();
  
      int currentPage = pageable.getPageNumber(); // 0-based
      int currentSection = PageUtil.getCurrentSection(currentPage, pageCountPerSection);
      int totalSection = PageUtil.getTotalSection(page.getTotalPages(), pageCountPerSection);
      List<Integer> pageList = PageUtil.getPageList(currentPage, page.getTotalPages(), pageCountPerSection);
  
      GetDailyListResponseDto responseBody = new GetDailyListResponseDto(
        list,
        page.getTotalPages(),
        page.getTotalElements(),
        currentPage + 1,  // 1-based page index
        currentSection,
        totalSection,
        pageList
      );
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  private Sort resolveSortOption(String sortOption) {
    switch (sortOption.toUpperCase()) {
      case "LIKES":
        return Sort.by(Sort.Order.desc("dailySequence"));
      case "VIEWS":
        return Sort.by(Sort.Order.desc("views"));
      default:
        return Sort.by(Sort.Order.desc("creationDate"), Sort.Order.desc("dailySequence"));
    }
  }
  
  // method: 일상 게시글 상세 조회 + 조회수 증가 //
  @Override
  public ResponseEntity<? super GetDailyResponseDto> getDailyBoardDetail(Integer dailySequence, String userId) {
    try {
      DailyEntity dailyEntity = dailyRepository.findById(dailySequence).orElse(null);
      if (dailyEntity == null) return ResponseDto.noExistDaily();
  
      dailyEntity.setViews(dailyEntity.getViews() + 1);
      dailyRepository.save(dailyEntity);
  
      int likeCount = dailyLikeRepository.countByDailySequence(dailySequence);
      boolean liked = dailyLikeRepository.existsByDailySequenceAndUserId(dailySequence, userId);
  
      List<DailyCommentEntity> commentEntities = dailyCommentRepository.findByDailySequenceOrderByCreationDateDesc(dailySequence);
  
      List<DailyCommentVO> commentList = commentEntities.stream()
        .map(comment -> {
          UserEntity user = userRepository.findByUserId(comment.getUserId());
          return new DailyCommentVO(comment, user);
        })
        .toList();
  
      String writerId = dailyEntity.getUserId();
      UserEntity writer = userRepository.findByUserId(writerId);
  
      return GetDailyResponseDto.success(dailyEntity, likeCount, commentList, writer, liked);
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 일상 게시글 수정 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> patchDailyBoard(PatchDailyRequestDto dto, Integer dailySequence, String userId) {
    try {
      DailyEntity dailyEntity = dailyRepository.findByDailySequence(dailySequence);
      if (dailyEntity == null) return ResponseDto.noExistDaily();

      if (!dailyEntity.getUserId().equals(userId)) return ResponseDto.noPermission();

      dailyEntity.patch(dto);
      dailyRepository.save(dailyEntity);

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 일상 게시글 삭제 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteDailyBoard(Integer dailySequence, String userId) {
    try {
      DailyEntity dailyEntity = dailyRepository.findByDailySequence(dailySequence);
      if (dailyEntity == null) return ResponseDto.noExistDaily();

      if (!dailyEntity.getUserId().equals(userId)) return ResponseDto.noPermission();

      dailyLikeRepository.deleteByDailySequence(dailySequence);
      dailyCommentRepository.deleteByDailySequence(dailySequence);
      dailyRepository.delete(dailyEntity);

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 일상 게시글 검색 //
  @Override
  public ResponseEntity<? super GetDailyListResponseDto> searchDailyBoardList(String keyword, Integer pageNumber) {
    try {
      int pageSize = 10;
      Sort sort = Sort.by("dailySequence").descending();
      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);
  
      Page<DailyEntity> page = dailyRepository.findByTitleContaining(keyword, pageable);
  
      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), page.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }
  
      List<DailySummaryResponseDto> list = page.stream()
        .map(entity -> {
          int likeCount = dailyLikeRepository.countByDailySequence(entity.getDailySequence());
          int commentCount = dailyCommentRepository.countByDailySequence(entity.getDailySequence());
  
          UserEntity user = userRepository.findById(entity.getUserId()).orElse(null);
          String profileImage = (user.getProfileImage() != null) ? user.getProfileImage() : null;
          String nickname = (user != null) ? user.getUserNickname() : null;
  
          return new DailySummaryResponseDto(
            entity.getDailySequence(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreationDate(),
            profileImage,
            nickname,
            entity.getViews(),
            likeCount,
            commentCount,
            entity.getImages()
          );
        })
        .toList();
  
      return ResponseEntity.ok(new GetDailyListResponseDto(list, pageNumber, pageSize));
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
  
  // method: 일상 게시글에 좋아요를 누르거나 취소 //
  @Override
  public ResponseEntity<ResponseDto> putDailyBoardLikeCount(Integer dailySequence, String userId) {
    try {
      boolean existDaily = dailyRepository.existsByDailySequence(dailySequence);
      if (!existDaily) return ResponseDto.noExistDaily();

      boolean hasLiked = dailyLikeRepository.existsByDailySequenceAndUserId(dailySequence, userId);
      if (hasLiked) {
        dailyLikeRepository.deleteByDailySequenceAndUserId(dailySequence, userId);
      } else {
        DailyLikeEntity like = new DailyLikeEntity();
        like.setDailySequence(dailySequence);
        like.setUserId(userId);
        dailyLikeRepository.save(like);
      }
      int likeCount = dailyLikeRepository.countByDailySequence(dailySequence);
    
      boolean liked = !hasLiked;

      return ResponseDto.success(HttpStatus.OK, new LikeData(likeCount, liked));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  public class LikeData {
    private int likeCount;
    private boolean liked;

    public LikeData(int likeCount, boolean liked) {
      this.likeCount = likeCount;
      this.liked = liked;
    }

    public int getLikeCount() {
      return likeCount;
    }

    public void setLikeCount(int likeCount) {
      this.likeCount = likeCount;
    }

    public boolean isLiked() {
      return liked;
    }

    public void setLiked(boolean liked) {
      this.liked = liked;
    }
  }

  // method: 일상 게시글에 좋아요 목록 조회 //
  @Override
  public ResponseEntity<? super GetLikedUserListResponseDto> getDailyBoardLikedUsers(Integer dailySequence) {
    try {
      boolean exists = dailyRepository.existsByDailySequence(dailySequence);
      if (!exists) return ResponseDto.noExistDaily();
  
      List<DailyLikeEntity> likeEntities = dailyLikeRepository.findByDailySequence(dailySequence);
  
      List<LikedUserDto> likedUsers = likeEntities.stream()
        .map(like -> {
          UserEntity user = userRepository.findByUserId(like.getUserId());
          return new LikedUserDto(user.getUserId(), user.getProfileImage(), user.getUserNickname());
        })
        .toList();

      int likeCount = dailyLikeRepository.countByDailySequence(dailySequence);
      return GetLikedUserListResponseDto.success(likedUsers, likeCount);
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 일상 게시글에 댓글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postDailyBoardComment(PostDailyCommentRequestDto dto, Integer dailySequence, String userId) {
    try {
      boolean exists = dailyRepository.existsByDailySequence(dailySequence);
      if (!exists) return ResponseDto.noExistDaily();

      DailyCommentEntity entity = new DailyCommentEntity(dto, dailySequence, userId);
      dailyCommentRepository.save(entity);

      return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 특정 게시글 댓글 불러오기 //
  @Override
  public ResponseEntity<? super GetDailyCommentResponseDto> getCommentsByDailySequence(Integer dailySequence) {

    List<DailyCommentEntity> commentEntities = new ArrayList<>();
    List<UserEntity> userEntities = new ArrayList<>();  // UserEntity 목록 추가

    try {

      commentEntities = dailyCommentRepository.findByDailySequenceOrderByCreationDateDesc(dailySequence);

      List<String> userIds = commentEntities.stream()
                                            .map(DailyCommentEntity::getUserId)
                                            .distinct()
                                            .collect(Collectors.toList());
      userEntities = userRepository.findByUserIdIn(userIds);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetDailyCommentResponseDto.success(commentEntities, userEntities);
  }

  // method: 일상 게시글에 댓글 삭제 (글작성자, 댓글작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteDailyComment(Integer commentSequence, String userId) {
    try {
      DailyCommentEntity comment = dailyCommentRepository.findById(commentSequence).orElse(null);
      if (comment == null) return ResponseDto.noExistComment();

      DailyEntity daily = dailyRepository.findById(comment.getDailySequence()).orElse(null);
      if (daily == null) return ResponseDto.noExistDaily();

      boolean isCommentWriter = comment.getUserId().equals(userId);
      boolean isPostWriter = daily.getUserId().equals(userId);

      if (!isCommentWriter && !isPostWriter) return ResponseDto.noPermission();

      dailyCommentRepository.deleteByCommentSequence(comment.getCommentSequence());
      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

}