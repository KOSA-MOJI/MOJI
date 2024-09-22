package com.spring.moji.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationInsertRequestDTO {
	private Long locationId;
	private Long pageId;
	private String address;
	private float latitude;
	private float longitude;
	private List<ImageUrlInsertRequestDTO> imageUrls;
}