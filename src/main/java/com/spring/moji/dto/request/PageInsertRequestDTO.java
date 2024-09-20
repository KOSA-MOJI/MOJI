package com.spring.moji.dto.request;

import com.spring.moji.entity.Location;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PageInsertRequestDTO {
  private Long pageId;
  private Long diaryId;
  private LocalDate createdAt;
  private String weather;
  private String content;
  private Character publicStatus;
  private Long templateId;
  private List<Location> locations;

}
