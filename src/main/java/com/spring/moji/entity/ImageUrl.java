package com.spring.moji.entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl {
	private Long imageUrlId;
	private Long locationId;
	private String mapImage;
	private LocalDate createdAt;
	@Builder
	public ImageUrl(Long imageUrlId, Long locationId, String mapImage, LocalDate createdAt) {
		this.imageUrlId = imageUrlId;
		this.locationId = locationId;
		this.mapImage = mapImage;
		this.createdAt = createdAt;
	}
}
