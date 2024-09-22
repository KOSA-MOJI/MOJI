package com.spring.moji.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.dto.request.DiaryRequestDTO;
import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Diary;
import com.spring.moji.entity.Page;
import com.spring.moji.entity.Template;
import com.spring.moji.mapper.DiaryMapper;
import com.spring.moji.mapper.PageMapper;
import com.spring.moji.mapper.TemplateMapper;
import com.spring.moji.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

	private final DiaryMapper diaryMapper;
	private final PageMapper pageMapper;
	private final TemplateMapper templateMapper;
	private final S3Util s3Util;

	@Override
	public Diary findByCoupleId(Long coupleId) {
		return diaryMapper.findByCoupleId(coupleId);
	}

	@Override
	public List<Page> fetchDiaryPages(Long diaryId, LocalDate startDate, LocalDate endDate) {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().diaryId(diaryId).startDate(startDate).endDate(endDate).build();
		return pageMapper.findByDuration(pageRequestDTO);
	}

	@Override
	@Transactional
	public String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException {
		// TODO : 기존 S3내 이미지 삭제 로직 추가
		String imageURL = s3Util.uploadFile(diaryCoverImage);
		DiaryRequestDTO diaryRequestDTO = DiaryRequestDTO.builder().diaryId(diaryId).coverImage(imageURL).build();
		diaryMapper.updateCoverImage(diaryRequestDTO);
		return imageURL;
	}

	@Override
	public List<Template> findAllTemplates() {
		return templateMapper.findAll();
	}
}
