package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageUrlInsertRequestDTO {
	private Long locationId;
	private String mapImage;
}
