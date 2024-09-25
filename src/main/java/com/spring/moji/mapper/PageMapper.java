package com.spring.moji.mapper;

import com.spring.moji.dto.request.CommunityRequestDTO;
import com.spring.moji.dto.request.PageInsertRequestDTO;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.moji.dto.request.PageRequestDTO;
import com.spring.moji.entity.Page;

@Mapper
public interface PageMapper {
	List<Page> findByDuration(PageRequestDTO pageRequestDTO);
	List<Page> findPublicByDistance(CommunityRequestDTO communityRequestDTO);
	Page findByPageId(Long pageId);
	// Page findRecentPage(PageRequestDTO pageRequestDTO);
	void deleteByPageId(Long pageId);
	void insertPage(PageInsertRequestDTO pageInsertRequestDTO);
	void updatePublicStatusByPageId(PageRequestDTO pageRequestDTO);
	List<LocalDate> findAllByCoupleId(Long coupleId);
}
