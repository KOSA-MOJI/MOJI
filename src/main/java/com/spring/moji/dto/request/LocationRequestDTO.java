package com.spring.moji.dto.request;

import com.spring.moji.entity.ImageUrl;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LocationRequestDTO {
  private Long locationId;
  private Long pageId;
  private String address;
  private float latitude;
  private float longitude;
  private LocalDate createdAt;
  private List<ImageUrl> imageUrls;
}
