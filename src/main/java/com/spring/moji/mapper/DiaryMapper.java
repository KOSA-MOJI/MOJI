package com.spring.moji.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.DiaryRequestDTO;
import com.spring.moji.entity.Diary;

@Mapper
public interface DiaryMapper {
	Diary findByCoupleId(Long coupleId);
	void updateCoverImage(DiaryRequestDTO diaryRequestDTO);
}
