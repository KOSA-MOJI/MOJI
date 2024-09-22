package com.spring.moji.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.dto.request.PageInsertRequestDTO;
import com.spring.moji.entity.Diary;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Template;

public interface DiaryService {
	Diary findByCoupleId(Long coupleId);
	List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate);
	String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException;
	List<Template> findAllTemplates();
	void setPagePublicStatus(Long pageId, boolean publicStatus);
	String preSigningImage(MultipartFile image);
	void deletePreSignedImage(String imageUrl);
	void createPage(PageInsertRequestDTO pageInsertRequestDTO);
	void deletePageById(Long pageId);
}
