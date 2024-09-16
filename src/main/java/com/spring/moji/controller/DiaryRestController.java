package com.spring.moji.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.entity.Diary;
import com.spring.moji.service.DiaryServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {
	private final DiaryServiceImpl diaryService;

	@GetMapping("/{diaryId}")
	public Diary getDiary(@PathVariable Long diaryId) {
		return diaryService.findByCoupleId(diaryId);
	}

	@PostMapping("/coverImage/{diaryId}")
	public String coverImage(@PathVariable Long diaryId, @RequestParam("diaryCoverImage")MultipartFile diaryCoverImage) throws
		IOException {
		return diaryService.updateCoverImage(diaryId,diaryCoverImage);
	}
}
