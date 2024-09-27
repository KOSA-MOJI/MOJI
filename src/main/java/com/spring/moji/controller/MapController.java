package com.spring.moji.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("user/map")
public class MapController {

	@Value("${kakao.api-key}")
	private String kakaoApiKey;

	@GetMapping()
	public String map(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		model.addAttribute("contentURL", "/WEB-INF/jsp/content/map/map-read.jsp");
		return "map/map-read-page";
	}
}
