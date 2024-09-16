package com.spring.moji.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.moji.entity.Template;
import com.spring.moji.mapper.TemplateMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
	private final TemplateMapper templateMapper;

	@Override
	public List<Template> findAll() {
		return templateMapper.findAll();
	}

	@Override
	public Template findById(Long templateId) {
		return templateMapper.findById(templateId);
	}
}
