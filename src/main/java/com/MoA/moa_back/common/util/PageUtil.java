package com.MoA.moa_back.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {

  private static final int DEFAULT_PAGE_SIZE = 10;

  public static Pageable createPageable(Integer pageNumber, Integer pageSize, Sort sort) {
    int size = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    int page = (pageNumber == null || pageNumber <= 0) ? 0 : pageNumber - 1;
    return PageRequest.of(page, size, sort);
  }

  public static boolean isInvalidPageIndex(int pageIndex, int totalPages) {
    return pageIndex < 0 || pageIndex >= totalPages;
  }

}

