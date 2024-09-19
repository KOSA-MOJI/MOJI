package com.spring.moji.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.moji.entity.Diary;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

	@Value("${kakao.api-key}")
	private String kakaoApiKey;

	@GetMapping()
	public String diary(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		return "diary/test";
	}
}
