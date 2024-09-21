package com.spring.moji.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityScrapRequestDTO {
	private String email;
	private List<Long> pageIds;
}
