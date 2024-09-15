package com.spring.moji.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

	@Override
	public String getDiaryPage() {

		return "return json";
	}
}
