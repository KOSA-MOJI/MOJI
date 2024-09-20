package com.spring.moji.entity;

import java.time.LocalDate;
import java.util.List;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
	private Long locationId;
	private Long pageId;
	private String address;
	private float latitude;
	private float longitude;
	private LocalDate createdAt;
	private List<ImageUrl> imageUrls;
	@Builder
	public Location(Long locationId, Long pageId, String address, float latitude, float longitude, LocalDate createdAt,
      List<ImageUrl> imageUrls) {
		this.locationId = locationId;
		this.pageId = pageId;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
    this.imageUrls = imageUrls;
  }

	public void setPageId(Long pageId) {

	}
}
