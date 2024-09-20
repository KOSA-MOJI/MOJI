package com.spring.moji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Page;

@Mapper
public interface PageMapper {
	List<Page> findByDuration(PageRequestDTO pageRequestDTO);
	Page findRecentPage(PageRequestDTO pageRequestDTO);
	int deleteByPageId(Long pageId);
}
