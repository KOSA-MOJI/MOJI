package com.spring.moji.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.service.DiaryServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {
	private final DiaryServiceImpl diaryService;

	@GetMapping
	public String getDiary() {
		return diaryService.getDiaryPage();
	}
}
