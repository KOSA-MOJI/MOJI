package com.spring.moji.service;

import java.time.LocalDate;
import java.util.List;

import com.spring.moji.entity.Page;

public interface PageService {
	List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate);
	Page findRecentPage(Long diaryId);
}
