package com.spring.moji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
	@GetMapping()
	public String diary() {
		return "diary/diary-page";
	}
}
