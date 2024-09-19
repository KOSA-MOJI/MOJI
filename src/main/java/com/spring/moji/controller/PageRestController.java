package com.spring.moji.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.entity.Page;
import com.spring.moji.service.PageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/page")
public class PageRestController {
	private final PageService pageService;
	//TODO: 조회 기능 보류;

	@GetMapping("/recent/{diaryId}")
	public Page findRecentPage(@PathVariable Long diaryId) {
		return pageService.findRecentPage(diaryId);
	}

	@GetMapping("/prefetch/{diaryId}")
	public List<Page> fetchDiaryPages(@PathVariable Long diaryId, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate startDate, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate endDate) {
		return pageService.fetchDiaryPages(diaryId, startDate, endDate);
	}

	//다이어리 내의 한 일자의 페이지 삭제
	@DeleteMapping("/delete/{pageId}")
	public ResponseEntity<String> deletePage(@PathVariable Long pageId) {
		pageService.deleteByPageId(pageId);
		return ResponseEntity.ok("Page deleted successfully");
	}
}
