package com.spring.moji.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.entity.Diary;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Template;

public interface DiaryService {
	Diary findByCoupleId(Long coupleId);
	List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate);
	String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException;
	List<Template> findAllTemplates();
	boolean setPagePublicStatus(Long pageId, boolean publicStatus);
}
