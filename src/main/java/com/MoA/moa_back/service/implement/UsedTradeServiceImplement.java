package com.MoA.moa_back.service.implement;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.usedtrade.PatchUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeListResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.UsedTradeSummaryResponseDto;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UsedTradeLikeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.util.PageUtil;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.UsedTradeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsedTradeServiceImplement implements UsedTradeService {

  private final UsedTradeRepository usedTradeRepository;
  private final UsedTradeLikeRepository usedTradeLikeRepository;
  private final UserRepository userRepository;

  // method: 중고거래 게시글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postUsedTrade(PostUsedTradeRequestDto dto, String userId) {
    try {
      UsedTradeEntity usedTradeEntity = new UsedTradeEntity(dto, userId);
      usedTradeRepository.save(usedTradeEntity);
      return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 중고거래 게시글 목록 조회 최신순 좋아요순 정렬 가능 (태그기준, 페이징) //
  @Override
  public ResponseEntity<? super GetUsedTradeListResponseDto> getUsedTradeListByTag(String tag, Integer pageNumber, String sortOption) {
    try {
      int pageSize = 10;
      int pageCountPerSection = 5;
  
      Sort sort = resolveSortOption(sortOption);
      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);
  
      ItemTypeTag itemTypeTag = resolveTag(tag);
  
      Page<UsedTradeEntity> page = (itemTypeTag == null)
        ? usedTradeRepository.findAll(pageable)
        : usedTradeRepository.findByItemTypeTag(itemTypeTag, pageable);
  
      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), page.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }
  
      List<UsedTradeSummaryResponseDto> list = page.stream()
        .map(this::buildUsedTradeSummaryResponseDto)
        .toList();
  
      if ("LIKES".equalsIgnoreCase(sortOption)) {
        list.sort(Comparator.comparingInt(UsedTradeSummaryResponseDto::getLikeCount).reversed());
      }
  
      // 페이징 정보
      int currentPage = pageable.getPageNumber();
      int currentSection = PageUtil.getCurrentSection(currentPage, pageCountPerSection);
      int totalSection = PageUtil.getTotalSection(page.getTotalPages(), pageCountPerSection);
      List<Integer> pageList = PageUtil.getPageList(currentPage, page.getTotalPages(), pageCountPerSection);
  
      // 성공 응답 반환
      return GetUsedTradeListResponseDto.success(
        list,
        page.getTotalPages(),
        page.getTotalElements(),
        currentPage + 1,
        currentSection,
        totalSection,
        pageList
      );
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
  
  private Sort resolveSortOption(String sortOption) {
    switch (sortOption.toUpperCase()) {
      case "LIKES":
        return Sort.by(Sort.Order.desc("tradeSequence"));
      case "VIEWS":
        return Sort.by(Sort.Order.desc("views"));
      default:
        return Sort.by(Sort.Order.desc("creationDate"), Sort.Order.desc("tradeSequence"));
    }
  }
  
  private ItemTypeTag resolveTag(String tag) {
    if (!"ALL".equalsIgnoreCase(tag)) {
      try {
        return ItemTypeTag.valueOf(tag);
      } catch (IllegalArgumentException e) {
        return null;
      }
    }
    return null;
  }
  
  private UsedTradeSummaryResponseDto buildUsedTradeSummaryResponseDto(UsedTradeEntity entity) {
    int likeCount = usedTradeLikeRepository.countByTradeSequence(entity.getTradeSequence());
    UserEntity user = userRepository.findByUserId(entity.getUserId());
  
    return new UsedTradeSummaryResponseDto(
      entity.getTradeSequence(),
      entity.getViews(),
      likeCount,
      entity.getCreationDate(),
      entity.getImages(),
      entity.getTitle(),
      user.getUserId(),
      entity.getLocation(),
      entity.getUsedItemStatusTag().name(),
      user.getProfileImage(),
      user.getUserNickname(),
      entity.getPrice()
    );
  }
  

  // method: 중고거래 게시글 상세 조회 + 조회수 증가 //
  @Override
  public ResponseEntity<? super GetUsedTradeResponseDto> getUsedTradeDetail(Integer tradeSequence) {
    try {
      UsedTradeEntity entity = usedTradeRepository.findById(tradeSequence).orElse(null);
      if (entity == null) return ResponseDto.noExistUsedTrade();

      entity.setViews(entity.getViews() + 1);
      usedTradeRepository.save(entity);

      UserEntity user = userRepository.findByUserId(entity.getUserId());
      int likeCount = usedTradeLikeRepository.countByTradeSequence(tradeSequence);

      boolean hasChatRoom = false;

      return GetUsedTradeResponseDto.success(entity, user, likeCount, hasChatRoom);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 중고거래 게시글 수정 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> patchUsedTrade(PatchUsedTradeRequestDto dto, Integer tradeSequence, String userId) {
    try {
      UsedTradeEntity entity = usedTradeRepository.findById(tradeSequence).orElse(null);
      if (entity == null) return ResponseDto.noExistUsedTrade();

      if (!entity.getUserId().equals(userId)) return ResponseDto.noPermission();

      entity.patch(dto);
      usedTradeRepository.save(entity);

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 중고거래글 (태그) 별 제목 검색 //
  @Override
  public ResponseEntity<? super GetUsedTradeListResponseDto> searchUsedTradeList(String tag, String keyword, Integer pageNumber) {
    try {
      int pageSize = 10;
      Sort sort = Sort.by("tradeSequence").descending();
      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);

      ItemTypeTag itemTypeTag = null;
      if (!"ALL".equalsIgnoreCase(tag)) {
        try {
          itemTypeTag = ItemTypeTag.valueOf(tag);
        } catch (IllegalArgumentException e) {
          return ResponseDto.invalidTag();
        }
      }

      Page<UsedTradeEntity> page = (itemTypeTag == null)
        ? usedTradeRepository.findByTitleContaining(keyword, pageable)
        : usedTradeRepository.findByItemTypeTagAndTitleContaining(itemTypeTag, keyword, pageable);

      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), page.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }

      List<UsedTradeSummaryResponseDto> list = page.stream()
        .map(entity -> {
          int likeCount = usedTradeLikeRepository.countByTradeSequence(entity.getTradeSequence());

          UserEntity user = userRepository.findById(entity.getUserId()).orElse(null);
          String profileImage = (user != null) ? user.getProfileImage() : null;
          String nickname = (user != null) ? user.getUserNickname() : null;

          return new UsedTradeSummaryResponseDto(
            entity.getTradeSequence(),
            entity.getViews(),
            likeCount,
            entity.getCreationDate(),
            entity.getImages(),
            entity.getTitle(),
            entity.getUserId(),
            entity.getLocation(),
            entity.getUsedItemStatusTag().toString(),
            profileImage,
            nickname,
            entity.getPrice()
          );
        })
        .toList();

      return ResponseEntity.status(HttpStatus.OK).body(new GetUsedTradeListResponseDto(list, pageNumber, pageSize));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 중고거래 게시글 삭제 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteUsedTrade(Integer tradeSequence, String userId) {
    try {
      UsedTradeEntity entity = usedTradeRepository.findById(tradeSequence).orElse(null);
      if (entity == null) return ResponseDto.noExistUsedTrade();

      if (!entity.getUserId().equals(userId)) return ResponseDto.noPermission();

      usedTradeLikeRepository.deleteByTradeSequence(tradeSequence);
      usedTradeRepository.delete(entity);

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 중고거래 게시글 찜 추가 또는 취소 //
  @Override
  public ResponseEntity<ResponseDto> putUsedTradeLikeCount(Integer tradeSequence, String userId) {
    try {
      boolean exists = usedTradeRepository.existsByTradeSequence(tradeSequence);
      if (!exists) return ResponseDto.noExistUsedTrade();

      boolean liked = usedTradeLikeRepository.existsByTradeSequenceAndUserId(tradeSequence, userId);
      if (liked) {
        usedTradeLikeRepository.deleteByTradeSequenceAndUserId(tradeSequence, userId);
      } else {
        UsedTradeLikeEntity like = new UsedTradeLikeEntity();
        like.setTradeSequence(tradeSequence);
        like.setUserId(userId);
        usedTradeLikeRepository.save(like);
      }

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
}
