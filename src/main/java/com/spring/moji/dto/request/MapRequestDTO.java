package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapRequestDTO {
	private Long coupleId;
	private String email;
}
