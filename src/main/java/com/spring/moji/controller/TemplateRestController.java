package com.spring.moji.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.moji.entity.Template;
import com.spring.moji.service.TemplateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class TemplateRestController {
	private final TemplateService templateService;
	@GetMapping
	public List<Template> findAll() {
		return templateService.findAll();
	}

}
