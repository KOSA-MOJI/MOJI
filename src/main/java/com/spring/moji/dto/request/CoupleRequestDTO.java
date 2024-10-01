package com.spring.moji.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoupleRequestDTO {
	private Long coupleId;
	private String requestEmail;
	private String receiverEmail;
}
