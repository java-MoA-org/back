package com.MoA.moa_back.common.dto.request.usedtrade;

import java.util.List;

import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.enums.UsedItemStatusTag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUsedTradeRequestDto {

  private String title;
  private String content;
  private ItemTypeTag itemTypeTag;
  private UsedItemStatusTag usedItemStatusTag;
  private String price;
  private String location;
  private String detailLocation;
  private List<String> imageList;
}
