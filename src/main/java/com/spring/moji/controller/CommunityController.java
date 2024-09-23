package com.spring.moji.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
	@Value("${kakao.api-key}")
	private String kakaoApiKey;
    //커뮤니티 컴포넌트 제대로 뜨는가
    @GetMapping()
    public String readCommunity(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("contentURL", "/WEB-INF/jsp/content/community/community-diary.jsp");
        return "community/community-diary-page";
    }
}
