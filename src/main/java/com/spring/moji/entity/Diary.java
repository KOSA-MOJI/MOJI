package com.spring.moji.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
	private Long diaryId;
	private Long coupleId;
	private String coverImage;
	private Bookmark bookmark;
	private List<Page> pages;

	@Builder
	public Diary(Long coupleId, Bookmark bookmark, String coverImage, Long diaryId) {
		this.diaryId = diaryId;
		this.coupleId = coupleId;
		this.bookmark = bookmark;
		this.coverImage = coverImage;
	}
}
