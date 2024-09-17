package com.spring.moji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.entity.Template;

@Mapper
public interface TemplateMapper {
	List<Template> findAll();
	Template findById(Long templateId);
}
