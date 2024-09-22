package com.spring.moji.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.entity.Diary;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Template;
import com.spring.moji.service.DiaryServiceImpl;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryRestController {
	private final DiaryServiceImpl diaryService;

	@GetMapping("/{coupleId}")
	public Diary getDiary(@PathVariable Long coupleId) {
		return diaryService.findByCoupleId(coupleId);
	}

	@GetMapping("/page/{diaryId}")
	public List<Page> getPages(@PathVariable Long diaryId, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate endDate){
		return diaryService.fetchDiaryPages(diaryId, startDate, endDate);
	}

	@PostMapping("/public/{pageId}")
	public boolean setPagePublicStatus(@PathVariable Long pageId, @RequestParam boolean publicStatus){
		diaryService.setPagePublicStatus(pageId,publicStatus);
		return true;
	}

	@GetMapping("/template")
	public List<Template> getTemplates() {
		return diaryService.findAllTemplates();
	}

	@PostMapping("/coverImage/{coupleId}")
	public String coverImage(@PathVariable Long coupleId, @RequestParam("diaryCoverImage")MultipartFile diaryCoverImage) throws
		IOException {
		return diaryService.updateCoverImage(coupleId,diaryCoverImage);
	}
}
