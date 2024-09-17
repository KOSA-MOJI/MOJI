package com.spring.moji.dto.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageRequestDTO {
	private Long diaryId;
	private LocalDate startDate;
	private LocalDate endDate;
}
