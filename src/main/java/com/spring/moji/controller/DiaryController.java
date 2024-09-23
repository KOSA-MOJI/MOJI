package com.spring.moji.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
@CrossOrigin(origins = "*")
public class DiaryController {

	@Value("${kakao.api-key}")
	private String kakaoApiKey;

	@Value("${weather.api.key}")
	private String weatherApiKey;

	private final ObjectMapper objectMapper;

	@GetMapping()
	public String diary(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		return "diary/test";
	}

	@GetMapping("/write")
	public String insertDiary(Model model) {
		// weatherApiKey를 모델에 추가하여 JSP로 전달
		model.addAttribute("weatherApiKey", weatherApiKey);
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/diary-write.jsp");
		return "diary/write-diary-page";
	}

	@GetMapping("/insertTotal")
	public String totalInsertDiary(Model model) {
		// weatherApiKey를 모델에 추가하여 JSP로 전달
		model.addAttribute("weatherApiKey", weatherApiKey);
		model.addAttribute("kakaoApiKey", kakaoApiKey);
		model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/total-write.jsp");
		return "diary/write-diary-page";
	}




}
