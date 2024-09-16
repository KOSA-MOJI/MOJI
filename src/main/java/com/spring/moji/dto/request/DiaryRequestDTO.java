package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiaryRequestDTO {
	private Long diaryId;
	private Long coupleId;
	private String coverImage;
	private Long bookmarkId;
}
