package com.spring.moji.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityResponseDTO {
	private Long pageId;
	private String imageUrl;
	private boolean isScrapped;
}
