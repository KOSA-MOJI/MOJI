package com.spring.moji.entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Page {
	private Long pageId;
	private Long diaryId;
	private LocalDate createdAt;
	private String wheather;
	private String content;
	private Character publicStatus;
	private Long templateId;
	@Builder
	public Page(Long pageId, Long diaryId, LocalDate createdAt, String wheather, String content, Character publicStatus, Long templateId) {
		this.pageId = pageId;
		this.diaryId = diaryId;
		this.createdAt = createdAt;
		this.wheather = wheather;
		this.content = content;
		this.publicStatus = publicStatus;
		this.templateId = templateId;
	}
}
