package com.spring.moji.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ImageUrlRequestDTO {
  private Long imageUrlId;
  private Long locationId;
  private String mapImage;
  private LocalDate createdAt;


}
