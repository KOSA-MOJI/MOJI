package com.spring.moji.entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {
	private Long templateId;
	private String templateImage;
	private LocalDate createdAt;

	@Builder
	public Template(Long templateId, String templateImage, LocalDate createdAt) {
		this.templateId = templateId;
		this.templateImage = templateImage;
		this.createdAt = createdAt;
	}
}
