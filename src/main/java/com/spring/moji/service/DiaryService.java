package com.spring.moji.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.entity.Diary;

public interface DiaryService {
	Diary findByCoupleId(Long coupleId);
	String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException;
}
