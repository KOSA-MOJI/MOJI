package com.spring.moji.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Page;
import com.spring.moji.mapper.PageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
	private final PageMapper pageMapper;

	@Override
	public List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).startDate(startDate).endDate(endDate).build();
		List<Page> pages = pageMapper.findByDuration(pageRequestDTO);
		return pages;
	}

	@Override
	public Page findRecentPage(Long diaryId) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).build();
		Page page = pageMapper.findRecentPage(pageRequestDTO);
		return page;
	}
}
