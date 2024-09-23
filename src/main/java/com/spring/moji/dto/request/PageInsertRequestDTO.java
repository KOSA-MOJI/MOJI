package com.spring.moji.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInsertRequestDTO {
  private Long pageId;
  private Long diaryId;
  private LocalDate createdAt;
  private String weather;
  private String content;
  private Long fontSize;
  private String fontColor;
  private String textAlignment;
  private Character publicStatus;
  private Long templateId;
  private List<LocationInsertRequestDTO> locations;
}
