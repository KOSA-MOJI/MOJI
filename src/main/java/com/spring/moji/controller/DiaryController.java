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
@RequestMapping("/user/couple/diary")
public class DiaryController {

  @Value("${kakao.api-key}")
  private String kakaoApiKey;

  @GetMapping()
  public String diaryRead(Model model) {
    model.addAttribute("kakaoApiKey", kakaoApiKey);
    model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/diary-read.jsp");
    return "diary/diary-read-page";
  }

  @GetMapping("/write")
  public String diaryWrite(Model model) {
    model.addAttribute("kakaoApiKey", kakaoApiKey);
	model.addAttribute("contentURL", "/WEB-INF/jsp/content/diary/diary-write.jsp");
	return "diary/diary-write-page";
  }
}
