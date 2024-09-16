package com.spring.moji.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.moji.dto.request.DiaryRequestDTO;
import com.spring.moji.entity.Diary;
import com.spring.moji.mapper.DiaryMapper;
import com.spring.moji.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

	private final DiaryMapper diaryMapper;
	private final S3Util s3Util;

	@Override
	public Diary findByCoupleId(Long coupleId) {
		Diary result = diaryMapper.findByCoupleId(coupleId);
		return result;
	}

	@Override
	public String updateCoverImage(Long diaryId, MultipartFile diaryCoverImage) throws IOException {
		// TODO : 기존 S3내 이미지 삭제 로직 추가
		String imageURL = s3Util.uploadFile(diaryCoverImage);
		DiaryRequestDTO diaryRequestDTO = DiaryRequestDTO.builder().diaryId(diaryId).coverImage(imageURL).build();
		diaryMapper.updateCoverImage(diaryRequestDTO);
		return imageURL;
	}
}
