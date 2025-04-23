package com.MoA.moa_back.common.util;

import java.util.ArrayList;
import java.util.List;

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

  public static List<Integer> getPageList(int currentPage, int totalPages, int pageCountPerSection) {
    int currentSection = currentPage / pageCountPerSection;
    int startPage = currentSection * pageCountPerSection;
    int endPage = Math.min(startPage + pageCountPerSection, totalPages);

    List<Integer> pageList = new ArrayList<>();
    for (int i = startPage; i < endPage; i++) {
      pageList.add(i + 1);
    }

    return pageList;
  }

  public static int getCurrentSection(int currentPage, int pageCountPerSection) {
    return currentPage / pageCountPerSection;
  }

  public static int getTotalSection(int totalPages, int pageCountPerSection) {
    return (int) Math.ceil((double) totalPages / pageCountPerSection);
  }
}
