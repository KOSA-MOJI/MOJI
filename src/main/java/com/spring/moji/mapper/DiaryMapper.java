package com.spring.moji.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.entity.Diary;

@Mapper
public interface DiaryMapper {
	Diary findByDiaryId(Long diaryId);
	Diary findByCoupleId(Long coupleId);
}
